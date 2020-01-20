package com.rbkmoney.cm.service;

import com.rbkmoney.cm.exception.*;
import com.rbkmoney.cm.model.*;
import com.rbkmoney.cm.model.status.StatusModificationModel;
import com.rbkmoney.cm.model.status.StatusModificationTypeEnum;
import com.rbkmoney.cm.pageable.ClaimPageRequest;
import com.rbkmoney.cm.pageable.ClaimPageResponse;
import com.rbkmoney.cm.repository.ClaimRepository;
import com.rbkmoney.cm.util.ClaimEventFactory;
import com.rbkmoney.cm.util.ContextUtil;
import com.rbkmoney.damsel.claim_management.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TBase;
import org.hibernate.Hibernate;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.rbkmoney.cm.repository.ClaimSpecifications.equalsByPartyIdAndClaimId;
import static com.rbkmoney.cm.repository.ClaimSpecifications.equalsByPartyIdClaimIdAndStatusIn;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@RequiredArgsConstructor
public class ClaimManagementServiceImpl implements ClaimManagementService {

    private final ClaimRepository claimRepository;

    private final ContinuationTokenService continuationTokenService;

    private final ConversionService conversionService;

    private final KafkaTemplate<String, TBase> kafkaTemplate;

    private final ClaimEventFactory claimEventFactory;

    private final RetryTemplate retryTemplate;

    @org.springframework.beans.factory.annotation.Value("${kafka.topic.claim.event.sink}")
    private String eventSinkTopic;

    @Override
    @Transactional
    public Claim createClaim(String partyId, List<Modification> changeset) {
        log.info("Trying to create new claim, partyId='{}', modifications='{}'", partyId, changeset);
        List<ModificationModel> modifications = changeset.stream()
                .map(change -> conversionService.convert(change, ModificationModel.class))
                .collect(Collectors.toList());

        ClaimStatusModel claimStatusModel = new ClaimStatusModel();
        claimStatusModel.setClaimStatusEnum(ClaimStatusEnum.pending);

        ClaimModel claimModel = new ClaimModel();
        claimModel.setPartyId(partyId);
        claimModel.setClaimStatus(claimStatusModel);
        modifications.forEach(this::addUserInfo);
        claimModel.setModifications(modifications);

        claimModel = claimRepository.saveAndFlush(claimModel);

        Claim claim = conversionService.convert(claimModel, Claim.class);
        sendToEventSinkWithRetry(String.valueOf(claim.getId()), claimEventFactory.createCreatedClaimEvent(partyId, changeset, claim));

        log.info("Claim have been created, partyId='{}', claim='{}'", partyId, claim);
        return claim;
    }

    @Override
    @Transactional
    public void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset) {
        log.info("Trying to update claim, partyId='{}', claimId='{}', revision='{}', modifications='{}'", partyId, claimId, revision, changeset);
        List<ModificationModel> modifications = changeset.stream()
                .map(change -> conversionService.convert(change, ModificationModel.class))
                .collect(Collectors.toList());

        ClaimModel claimModel = getClaim(partyId, claimId, false);
        checkRevision(claimModel, revision);
        checkStatus(claimModel, Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review));
        checkForConflicts(claimModel.getModifications(), modifications);

        modifications.forEach(this::addUserInfo);
        claimModel.getModifications().addAll(modifications);

        claimModel = claimRepository.saveAndFlush(claimModel);

        sendToEventSinkWithRetry(String.valueOf(claimId), claimEventFactory.createUpdateClaimEvent(partyId, claimId, revision, changeset, claimModel.getUpdatedAt()));

        log.info("Claim have been updated, partyId='{}', claim='{}'", partyId, claimModel);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ClaimModel getClaim(String partyId, long claimId) {
        return getClaim(partyId, claimId, true);
    }

    private ClaimModel getClaim(String partyId, long claimId, boolean needInitialize) {
        ClaimModel claimModel = claimRepository.findOne(
                where(equalsByPartyIdAndClaimId(partyId, claimId))
        ).orElseThrow(ClaimNotFoundException::new);

        if (needInitialize) {
            initializeClaim(claimModel);
        }

        return claimModel;
    }

    @Override
    @Transactional
    public ClaimModel acceptClaim(String partyId, long claimId, int revision) {
        ClaimModel claimModel = changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.pending_acceptance, null),
                Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review)
        );
        sendToEventSinkWithRetry(String.valueOf(claimId), claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.accepted(new ClaimAccepted()), claimModel.getUpdatedAt()));
        return claimModel;
    }

    @Override
    @Transactional
    public ClaimModel revokeClaim(String partyId, long claimId, int revision, String reason) {
        ClaimModel claimModel = changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.revoked, reason),
                Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review)
        );
        sendToEventSinkWithRetry(String.valueOf(claimId), claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.revoked(new ClaimRevoked()), claimModel.getUpdatedAt()));
        return claimModel;
    }

    @Override
    @Transactional
    public ClaimModel denyClaim(String partyId, long claimId, int revision, String reason) {
        ClaimModel claimModel = changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.denied, reason),
                Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review)
        );
        sendToEventSinkWithRetry(String.valueOf(claimId), claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.denied(new ClaimDenied()), claimModel.getUpdatedAt()));
        return claimModel;
    }

    @Override
    @Transactional
    public ClaimModel requestClaimReview(String partyId, long claimId, int revision) {
        ClaimModel claimModel = changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.review, null),
                Collections.singletonList(ClaimStatusEnum.pending)
        );
        sendToEventSinkWithRetry(String.valueOf(claimId), claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.review(new ClaimReview()), claimModel.getUpdatedAt()));
        return claimModel;
    }

    @Override
    @Transactional
    public ClaimModel requestClaimChanges(String partyId, long claimId, int revision) {
        ClaimModel claimModel = changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.pending, null),
                Collections.singletonList(ClaimStatusEnum.review)
        );
        sendToEventSinkWithRetry(
                String.valueOf(claimId),
                claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.pending(new ClaimPending()), claimModel.getUpdatedAt())
        );
        return claimModel;
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

        log.info("Status in claim have been changed, claimId='{}', targetStatus='{}'", claimId, targetClaimStatus);

        return claimRepository.save(claimModel);
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

    private void checkForConflicts(List<ModificationModel> oldModifications, List<ModificationModel> newModifications) {
        for (ModificationModel newModification : newModifications) {
            for (ModificationModel oldModification : oldModifications) {
                if (oldModification.canEqual(newModification)) {
                    log.warn("Found conflict in modifications, oldModification='{}', newModification='{}'", oldModification, newModification);
                    throw new ChangesetConflictException(oldModification.getId());
                }
            }
        }
    }

    private void checkStatus(ClaimModel claimModel, List<ClaimStatusEnum> expectedStatuses) {
        if (!expectedStatuses.isEmpty() && !expectedStatuses.contains(claimModel.getClaimStatus().getClaimStatusEnum())) {
            log.warn("Invalid claim status, expected='{}', actual='{}'", expectedStatuses, claimModel.getClaimStatus().getClaimStatusEnum());
            throw new InvalidClaimStatusException(claimModel.getClaimStatus());
        }
    }

    private void checkRevision(ClaimModel claimModel, int revision) {
        if (claimModel.getRevision() != revision) {
            log.warn("Invalid claim revision, expected='{}', actual='{}'", claimModel.getRevision(), revision);
            throw new InvalidRevisionException();
        }
    }

    private void initializeClaim(ClaimModel claimModel) {
        Hibernate.initialize(claimModel.getModifications());
        Hibernate.initialize(claimModel.getMetadata());
    }

    private ModificationModel addUserInfo(ModificationModel modificationModel) {
        modificationModel.setUserInfo(ContextUtil.getUserInfoFromContext());
        return modificationModel;
    }

    private void sendToEventSinkWithRetry(String claimId, Event event) {
        retryTemplate.execute(arg0 -> {
            kafkaTemplate.send(eventSinkTopic, claimId, event);
            return null;
        });
    }
}
