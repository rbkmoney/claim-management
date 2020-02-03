package com.rbkmoney.cm.service.impl;

import com.rbkmoney.cm.exception.*;
import com.rbkmoney.cm.model.*;
import com.rbkmoney.cm.model.status.StatusModificationModel;
import com.rbkmoney.cm.model.status.StatusModificationTypeEnum;
import com.rbkmoney.cm.pageable.ClaimPageRequest;
import com.rbkmoney.cm.pageable.ClaimPageResponse;
import com.rbkmoney.cm.repository.ClaimRepository;
import com.rbkmoney.cm.service.ClaimManagementService;
import com.rbkmoney.cm.service.ContinuationTokenService;
import com.rbkmoney.cm.service.ConversionWrapperService;
import com.rbkmoney.cm.util.ClaimEventFactory;
import com.rbkmoney.cm.util.ContextUtil;
import com.rbkmoney.damsel.claim_management.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TBase;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.concurrent.ListenableFuture;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.rbkmoney.cm.repository.ClaimSpecifications.equalsByPartyIdAndClaimId;
import static com.rbkmoney.cm.repository.ClaimSpecifications.equalsByPartyIdClaimIdAndStatusIn;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@RequiredArgsConstructor
public class ClaimManagementServiceImpl implements ClaimManagementService {

    private final ContinuationTokenService continuationTokenService;

    private final ConversionWrapperService conversionWrapperService;

    private final ClaimRepository claimRepository;

    private final ClaimEventFactory claimEventFactory;

    private final KafkaTemplate<String, TBase> kafkaTemplate;

    private final RetryTemplate retryTemplate;

    private final String eventSinkTopic;

    @Override
    @Transactional
    public Claim createClaim(String partyId, List<Modification> changeset) {
        log.info("Trying to create new claim, partyId='{}', modifications='{}'", partyId, changeset);

        List<ModificationModel> modifications = conversionWrapperService.convertModifications(changeset);

        checkEquals(modifications, modifications);

        ClaimStatusModel claimStatusModel = new ClaimStatusModel();
        claimStatusModel.setClaimStatusEnum(ClaimStatusEnum.pending);

        ClaimModel claimModel = new ClaimModel();
        claimModel.setPartyId(partyId);
        claimModel.setClaimStatus(claimStatusModel);

        modifications.forEach(this::addUserInfo);
        claimModel.setModifications(modifications);

        claimModel = claimRepository.saveAndFlush(claimModel);

        Claim claim = conversionWrapperService.convertClaim(claimModel);

        Event claimEvent = claimEventFactory.createCreatedClaimEvent(partyId, changeset, claim);

        sendToEventSinkWithRetry(partyId, claimEvent);

        log.info("Claim have been created, partyId='{}', claim='{}'", partyId, claim);

        return claim;
    }

    @Override
    @Transactional
    public void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset) {
        log.info("Trying to update claim, partyId='{}', claimId='{}', revision='{}', modifications='{}'", partyId, claimId, revision, changeset);

        List<ModificationModel> modifications = conversionWrapperService.convertModifications(changeset);

        checkEquals(modifications, modifications);

        ClaimModel claimModel = getClaim(partyId, claimId, false);

        checkRevision(claimModel, revision);
        checkStatus(claimModel, Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review));
        checkForConflicts(claimModel.getModifications(), modifications);

        modifications.forEach(this::addUserInfo);
        claimModel.getModifications().addAll(modifications);

        claimModel = claimRepository.saveAndFlush(claimModel);

        Event claimEvent = claimEventFactory.createUpdateClaimEvent(partyId, claimId, claimModel.getRevision(), changeset, claimModel.getUpdatedAt());

        sendToEventSinkWithRetry(partyId, claimEvent);

        log.info("Claim have been updated, partyId='{}', claim='{}'", partyId, claimModel);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ClaimModel getClaim(String partyId, long claimId) {
        return getClaim(partyId, claimId, true);
    }

    @Override
    @Transactional
    public ClaimModel pendingAcceptanceClaim(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.pending_acceptance, null),
                Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review)
        );
    }

    @Override
    @Transactional
    public ClaimModel failClaimAcceptance(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.pending, null),
                Collections.singletonList(ClaimStatusEnum.pending_acceptance)
        );
    }

    @Override
    @Transactional
    public ClaimModel acceptClaim(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.accepted, null),
                Arrays.asList(ClaimStatusEnum.pending_acceptance)
        );
    }

    @Override
    @Transactional
    public ClaimModel revokeClaim(String partyId, long claimId, int revision, String reason) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.revoked, reason),
                Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review)
        );
    }

    @Override
    @Transactional
    public ClaimModel denyClaim(String partyId, long claimId, int revision, String reason) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.denied, reason),
                Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review)
        );
    }

    @Override
    @Transactional
    public ClaimModel requestClaimReview(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.review, null),
                Collections.singletonList(ClaimStatusEnum.pending)
        );
    }

    @Override
    @Transactional
    public ClaimModel requestClaimChanges(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.pending, null),
                Collections.singletonList(ClaimStatusEnum.review)
        );
    }

    @Override
    public ClaimModel changeStatus(String partyId, long claimId, int revision, ClaimStatusModel targetClaimStatus, List<ClaimStatusEnum> expectedStatuses) {
        log.info("Trying to change status in claim, claimId='{}', targetStatus='{}'", claimId, targetClaimStatus);

        ClaimModel claimModel = getClaim(partyId, claimId, false);

        if (claimModel.getClaimStatus().getClaimStatusEnum() == targetClaimStatus.getClaimStatusEnum()) {
            log.info("Claim is already in target status, status='{}'", targetClaimStatus);
            return claimModel;
        }

        checkRevision(claimModel, revision);
        checkStatus(claimModel, expectedStatuses);

        claimModel.setClaimStatus(targetClaimStatus);

        StatusModificationModel statusModificationModel = new StatusModificationModel();
        statusModificationModel.setClaimStatus(targetClaimStatus);
        statusModificationModel.setStatusModificationType(StatusModificationTypeEnum.change);
        statusModificationModel.setUserInfo(ContextUtil.getUserInfoFromContext());
        claimModel.getModifications().add(statusModificationModel);

        claimModel = claimRepository.saveAndFlush(claimModel);

        Event claimEvent = claimEventFactory.createChangeStatusEvent(
                partyId, claimId, claimModel.getRevision(),
                conversionWrapperService.convertClaimStatus(claimModel.getClaimStatus()),
                claimModel.getUpdatedAt()
        );

        sendToEventSinkWithRetry(partyId, claimEvent);
        log.info("Status in claim have been changed, claimId='{}', targetStatus='{}'", claimId, targetClaimStatus);

        return claimModel;
    }

    @Override
    @Transactional
    public ClaimPageResponse searchClaims(String partyId, Long claimId, List<ClaimStatusEnum> statuses, String continuationToken, int limit) {
        List<Object> parameters = Arrays.asList(partyId, claimId, statuses, limit);
        ClaimPageRequest claimPageRequest = new ClaimPageRequest(0, limit);
        if (continuationToken != null) {
            int pageNumber = continuationTokenService.validateAndGet(continuationToken, Integer.class, parameters);
            claimPageRequest.setPage(pageNumber);
        }

        Page<ClaimModel> claimsPage = searchClaims(partyId, claimId, statuses, claimPageRequest);

        return new ClaimPageResponse(
                claimsPage.getContent(),
                claimsPage.hasNext() ? continuationTokenService.buildToken(claimsPage.getPageable().next().getPageNumber(), parameters) : null
        );
    }

    @Override
    @Transactional
    public Page<ClaimModel> searchClaims(String partyId, Long claimId, List<ClaimStatusEnum> statuses, ClaimPageRequest claimPageRequest) {
        log.info("Trying to search claims, partyId='{}', statuses='{}', pageRequest='{}'", partyId, statuses, claimPageRequest);

        Page<ClaimModel> claims = claimRepository.findAll(
                equalsByPartyIdClaimIdAndStatusIn(partyId, claimId, statuses),
                PageRequest.of(claimPageRequest.getPage(), claimPageRequest.getLimit(), Sort.Direction.DESC, "id")
        );

        claims.getContent().forEach(this::initializeClaim);

        log.info("{} claims have been found", claims.getTotalElements());

        return claims;
    }

    @Override
    @Transactional
    public MetadataModel getMetadata(String partyId, long claimId, String key) {
        log.info("Trying to get metadata field, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);

        ClaimModel claimModel = getClaim(partyId, claimId, false);

        MetadataModel metadataModel = claimModel.getMetadata().stream()
                .filter(metadata -> key.equals(metadata.getKey()))
                .findFirst()
                .orElseThrow(MetadataKeyNotFoundException::new);

        log.info("Metadata field have been found, metadata='{}'", metadataModel);

        return metadataModel;
    }

    @Override
    @Transactional
    public void setMetadata(String partyId, long claimId, String key, MetadataModel metadataModel) {
        log.info("Trying to change metadata field, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);

        ClaimModel claimModel = getClaim(partyId, claimId, false);
        claimModel.getMetadata().removeIf(metadata -> key.equals(metadata.getKey()));
        claimModel.getMetadata().add(metadataModel);

        claimRepository.save(claimModel);

        log.info("metadata field have been changed, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);
    }

    @Override
    @Transactional
    public void removeMetadata(String partyId, long claimId, String key) {
        log.info("Trying to remove metadata field, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);

        ClaimModel claimModel = getClaim(partyId, claimId, false);
        claimModel.getMetadata().removeIf(metadata -> key.equals(metadata.getKey()));

        claimRepository.save(claimModel);

        log.info("metadata field have been removed, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);
    }

    private ClaimModel getClaim(String partyId, long claimId, boolean needInitialize) {
        ClaimModel claimModel = claimRepository.findOne(
                where(equalsByPartyIdAndClaimId(partyId, claimId))
        )
                .orElseThrow(ClaimNotFoundException::new);

        if (needInitialize) {
            initializeClaim(claimModel);
        }

        return claimModel;
    }

    private void checkForConflicts(List<ModificationModel> oldModifications, List<ModificationModel> newModifications) {
        for (ModificationModel newModification : newModifications) {
            for (ModificationModel oldModification : oldModifications) {
                if (oldModification.canEqual(newModification)) {
                    throw new ChangesetConflictException(
                            String.format("Found conflict in modifications, oldModification='%s', newModification='%s'", oldModification, newModification),
                            oldModification.getId()
                    );
                }
            }
        }
    }

    private void checkStatus(ClaimModel claimModel, List<ClaimStatusEnum> expectedStatuses) {
        if (!expectedStatuses.isEmpty() && !expectedStatuses.contains(claimModel.getClaimStatus().getClaimStatusEnum())) {
            throw new InvalidClaimStatusException(
                    String.format("Invalid claim status, expected='%s', actual='%s'", expectedStatuses, claimModel.getClaimStatus().getClaimStatusEnum()),
                    claimModel.getClaimStatus()
            );
        }
    }

    private void checkRevision(ClaimModel claimModel, int revision) {
        if (claimModel.getRevision() != revision) {
            throw new InvalidRevisionException(
                    String.format("Invalid claim revision, expected='%s', actual='%s'", claimModel.getRevision(), revision)
            );
        }
    }

    private void checkEquals(List<ModificationModel> oldModifications, List<ModificationModel> newModifications) {
        List<ModificationModel> conflictModifications = new ArrayList<>();

        for (int i = 0; i < newModifications.size() - 1; i++) {
            for (int j = i + 1; j < oldModifications.size(); j++) {
                if (newModifications.get(i).canEqual(oldModifications.get(j))) {
                    conflictModifications.add(oldModifications.get(j));
                }
            }
        }

        if (!conflictModifications.isEmpty()) {
            throw new InvalidChangesetException(
                    String.format("ModificationModels contains doubles, count=%s", conflictModifications.size()),
                    conflictModifications
            );
        }
    }

    private void initializeClaim(ClaimModel claimModel) {
        Hibernate.initialize(claimModel.getModifications());
        Hibernate.initialize(claimModel.getMetadata());
    }

    private void addUserInfo(ModificationModel modificationModel) {
        modificationModel.setUserInfo(ContextUtil.getUserInfoFromContext());
    }

    private void sendToEventSinkWithRetry(String partyId, Event event) {
        retryTemplate.execute(
                retryCallback -> {
                    try {
                        ListenableFuture<SendResult<String, TBase>> future = kafkaTemplate.send(eventSinkTopic, partyId, event);
                        future.get();
                    } catch (InterruptedException e) {
                        log.error("Error when sendToEventSinkWithRetry partyId: {}, event: {}", partyId, event, e);
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException e) {
                        log.error("Error when sendToEventSinkWithRetry partyId: {}, event: {}", partyId, event, e);
                        throw new RuntimeException("Error when sendToEventSinkWithRetry", e);
                    }
                    return null;
                }
        );
    }
}
