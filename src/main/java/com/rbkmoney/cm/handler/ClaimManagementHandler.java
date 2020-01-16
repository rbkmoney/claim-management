package com.rbkmoney.cm.handler;

import com.rbkmoney.cm.exception.*;
import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.ClaimStatusEnum;
import com.rbkmoney.cm.model.MetadataModel;
import com.rbkmoney.cm.model.ModificationModel;
import com.rbkmoney.cm.pageable.ClaimPageResponse;
import com.rbkmoney.cm.service.ClaimManagementService;
import com.rbkmoney.cm.service.ConversionWrapperService;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.geck.common.util.TBaseUtil;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClaimManagementHandler implements ClaimManagementSrv.Iface {

    private final long limit;

    private final ClaimManagementService claimManagementService;

    private final ConversionWrapperService conversionWrapperService;

    @Override
    public Claim createClaim(String partyId, List<Modification> changeset) throws InvalidChangeset, TException {
        try {
            List<ModificationModel> modifications = conversionWrapperService.convertModifications(changeset);
            ClaimModel claimModel = claimManagementService.createClaim(partyId, modifications);
            return conversionWrapperService.convertClaim(claimModel);
        } catch (InvalidChangesetException ex) {
            throw new InvalidChangeset(ex.getMessage(), conversionWrapperService.convertModificationModels(ex.getModifications()));
        }
    }

    @Override
    public Claim getClaim(String partyId, long claimId) throws ClaimNotFound, TException {
        try {
            ClaimModel claimModel = claimManagementService.getClaim(partyId, claimId);
            return conversionWrapperService.convertClaim(claimModel);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        }
    }

    @Override
    public ClaimSearchResponse searchClaims(ClaimSearchQuery claimRequest) throws LimitExceeded, BadContinuationToken, TException {
        try {
            if (claimRequest.getLimit() > limit) {
                throw new LimitExceededException(String.format("Limit from request '%s more than can be by service '%s'", claimRequest.getLimit(), limit));
            }

            List<ClaimStatusEnum> claimStatusEnums = Optional.ofNullable(claimRequest.getStatuses())
                    .map(
                            statuses -> statuses.stream()
                                    .map(status -> TBaseUtil.unionFieldToEnum(status, ClaimStatusEnum.class))
                                    .collect(Collectors.toList())
                    )
                    .orElse(null);

            ClaimPageResponse claimsWithContinuationToken = claimManagementService.searchClaims(
                    claimRequest.getPartyId(),
                    claimRequest.isSetClaimId() ? claimRequest.getClaimId() : null,
                    claimStatusEnums,
                    claimRequest.getContinuationToken(),
                    claimRequest.getLimit()
            );

            return new ClaimSearchResponse()
                    .setResult(
                            claimsWithContinuationToken.getClaims().stream()
                                    .map(conversionWrapperService::convertClaim)
                                    .collect(Collectors.toList())
                    )
                    .setContinuationToken(claimsWithContinuationToken.getToken());
        } catch (BadContinuationTokenException ex) {
            throw new BadContinuationToken(ex.getMessage());
        } catch (LimitExceededException ex) {
            throw new LimitExceeded(ex.getMessage());
        }
    }

    @Override
    public void acceptClaim(String partyId, long claimId, int revision) throws ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        try {
            claimManagementService.acceptClaim(partyId, claimId, revision);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionWrapperService.convertClaimStatus(ex));
        }
    }

    @Override
    public void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset) throws ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, ChangesetConflict, InvalidChangeset, TException {
        try {
            List<ModificationModel> modifications = conversionWrapperService.convertModifications(changeset);
            claimManagementService.updateClaim(partyId, claimId, revision, modifications);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionWrapperService.convertClaimStatus(ex));
        } catch (ChangesetConflictException ex) {
            throw new ChangesetConflict(ex.getConflictedId());
        } catch (InvalidChangesetException ex) {
            throw new InvalidChangeset(ex.getMessage(), conversionWrapperService.convertModificationModels(ex.getModifications()));
        }
    }

    @Override
    public void requestClaimReview(String partyId, long claimId, int revision) throws ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        try {
            claimManagementService.requestClaimReview(partyId, claimId, revision);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionWrapperService.convertClaimStatus(ex));
        }
    }

    @Override
    public void requestClaimChanges(String partyId, long claimId, int revision) throws ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        try {
            claimManagementService.requestClaimChanges(partyId, claimId, revision);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionWrapperService.convertClaimStatus(ex));
        }
    }

    @Override
    public void denyClaim(String partyId, long claimId, int revision, String reason) throws ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        try {
            claimManagementService.denyClaim(partyId, claimId, revision, reason);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionWrapperService.convertClaimStatus(ex));
        }
    }

    @Override
    public void revokeClaim(String partyId, long claimId, int revision, String reason) throws ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        try {
            claimManagementService.revokeClaim(partyId, claimId, revision, reason);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionWrapperService.convertClaimStatus(ex));
        }
    }

    @Override
    public Value getMetadata(String partyId, long claimId, String key) throws ClaimNotFound, MetadataKeyNotFound, TException {
        try {
            MetadataModel metadataModel = claimManagementService.getMetadata(partyId, claimId, key);
            return conversionWrapperService.convertMsgpackValue(metadataModel);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (MetadataKeyNotFoundException ex) {
            throw new MetadataKeyNotFound();
        }
    }

    @Override
    public void setMetadata(String partyId, long claimId, String key, Value value) throws ClaimNotFound, TException {
        MetadataModel metadataModel = conversionWrapperService.convertMetadataModel(key, value);
        try {
            claimManagementService.setMetadata(partyId, claimId, key, metadataModel);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        }
    }

    @Override
    public void removeMetadata(String partyId, long claimId, String key) throws ClaimNotFound, TException {
        try {
            claimManagementService.removeMetadata(partyId, claimId, key);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        }
    }
}
