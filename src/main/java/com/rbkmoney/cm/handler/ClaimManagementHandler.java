package com.rbkmoney.cm.handler;

import com.rbkmoney.cm.exception.*;
import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.ClaimStatusEnum;
import com.rbkmoney.cm.model.MetadataModel;
import com.rbkmoney.cm.model.ModificationModel;
import com.rbkmoney.cm.pageable.ClaimPageResponse;
import com.rbkmoney.cm.service.ClaimManagementService;
import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.geck.common.util.TBaseUtil;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClaimManagementHandler implements ClaimManagementSrv.Iface {

    private final ClaimManagementService claimManagementService;

    private final ConversionService conversionService;

    @Override
    public Claim createClaim(String partyId, List<Modification> changeset) throws PartyNotFound, ChangesetConflict, InvalidChangeset, InvalidRequest, TException {
        List<ModificationModel> modifications = changeset.stream()
                .map(change -> conversionService.convert(change, ModificationModel.class))
                .collect(Collectors.toList());
        ClaimModel claimModel = claimManagementService.createClaim(partyId, modifications);
        return conversionService.convert(claimModel, Claim.class);
    }

    @Override
    public void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset) throws PartyNotFound, ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, ChangesetConflict, InvalidChangeset, TException {
        List<ModificationModel> modifications = changeset.stream()
                .map(change -> conversionService.convert(change, ModificationModel.class))
                .collect(Collectors.toList());
        try {
            claimManagementService.updateClaim(partyId, claimId, revision, modifications);
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionService.convert(ex.getClaimStatusModel(), ClaimStatus.class));
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (ChangesetConflictException ex) {
            throw new ChangesetConflict(ex.getConflictedId());
        }
    }

    @Override
    public Claim getClaim(String partyId, long claimId) throws PartyNotFound, ClaimNotFound, TException {
        try {
            ClaimModel claimModel = claimManagementService.getClaim(partyId, claimId);
            return conversionService.convert(claimModel, Claim.class);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        }
    }

    @Override
    public ClaimSearchResponse searchClaims(ClaimSearchQuery claimRequest) throws PartyNotFound, LimitExceeded, BadContinuationToken, TException {
        List<ClaimStatusEnum> claimStatusEnums = Optional.ofNullable(claimRequest.getStatuses())
                .map(
                        statuses -> statuses.stream()
                                .map(status -> TBaseUtil.unionFieldToEnum(status, ClaimStatusEnum.class))
                                .collect(Collectors.toList())
                )
                .orElse(null);

        try {
            ClaimPageResponse claimsWithContinuationToken = claimManagementService.searchClaims(
                    claimRequest.getPartyId(),
                    claimStatusEnums,
                    claimRequest.getContinuationToken(),
                    claimRequest.getLimit()
            );

            return new ClaimSearchResponse()
                    .setResult(
                            claimsWithContinuationToken.getClaims().stream()
                                    .map(claimModel -> conversionService.convert(claimModel, Claim.class))
                                    .collect(Collectors.toList())
                    )
                    .setContinuationToken(claimsWithContinuationToken.getToken());
        } catch (InvalidContinuationTokenException ex) {
            throw new BadContinuationToken(ex.getMessage());
        }
    }

    @Override
    public void acceptClaim(String partyId, long claimId, int revision) throws PartyNotFound, ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, InvalidChangeset, TException {
        try {
            claimManagementService.acceptClaim(partyId, claimId, revision);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionService.convert(ex.getClaimStatusModel(), ClaimStatus.class));
        }
    }

    @Override
    public void denyClaim(String partyId, long claimId, int revision, String reason) throws PartyNotFound, ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        try {
            claimManagementService.denyClaim(partyId, claimId, revision, reason);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionService.convert(ex.getClaimStatusModel(), ClaimStatus.class));
        }
    }

    @Override
    public void revokeClaim(String partyId, long claimId, int revision, String reason) throws PartyNotFound, ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        try {
            claimManagementService.revokeClaim(partyId, claimId, revision, reason);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionService.convert(ex.getClaimStatusModel(), ClaimStatus.class));
        }
    }


    @Override
    public void requestClaimReview(String partyId, long claimId, int revision) throws PartyNotFound, ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        try {
            claimManagementService.requestClaimReview(partyId, claimId, revision);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionService.convert(ex.getClaimStatusModel(), ClaimStatus.class));
        }
    }

    @Override
    public void requestClaimChanges(String partyId, long claimId, int revision) throws PartyNotFound, ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        try {
            claimManagementService.requestClaimChanges(partyId, claimId, revision);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (InvalidRevisionException ex) {
            throw new InvalidClaimRevision();
        } catch (InvalidClaimStatusException ex) {
            throw new InvalidClaimStatus(conversionService.convert(ex.getClaimStatusModel(), ClaimStatus.class));
        }
    }

    @Override
    public Value getMetadata(String partyId, long claimId, String key) throws PartyNotFound, ClaimNotFound, MetadataKeyNotFound, TException {
        try {
            MetadataModel metadataModel = claimManagementService.getMetadata(partyId, claimId, key);
            return conversionService.convert(metadataModel, Value.class);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        } catch (MetadataKeyNotFoundException ex) {
            throw new MetadataKeyNotFound();
        }
    }

    @Override
    public void setMetadata(String partyId, long claimId, String key, Value value) throws PartyNotFound, ClaimNotFound, TException {
        MetadataModel metadataModel = conversionService.convert(Map.entry(key, value), MetadataModel.class);
        try {
            claimManagementService.setMetadata(partyId, claimId, key, metadataModel);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        }
    }

    @Override
    public void removeMetadata(String partyId, long claimId, String key) throws PartyNotFound, ClaimNotFound, MetadataKeyNotFound, TException {
        try {
            claimManagementService.removeMetadata(partyId, claimId, key);
        } catch (ClaimNotFoundException ex) {
            throw new ClaimNotFound();
        }
    }
}
