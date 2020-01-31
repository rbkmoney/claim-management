package com.rbkmoney.cm.service;

import com.rbkmoney.cm.config.CommitterConfig;
import com.rbkmoney.cm.exception.InvalidChangesetException;
import com.rbkmoney.cm.exception.InvalidClaimStatusException;
import com.rbkmoney.cm.exception.InvalidRevisionException;
import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.ClaimStatusEnum;
import com.rbkmoney.damsel.claim_management.Claim;
import com.rbkmoney.damsel.claim_management.ClaimCommitterSrv;
import com.rbkmoney.damsel.claim_management.InvalidChangeset;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ClaimCommitterService {

    private final ClaimManagementService claimManagementService;

    private final ConversionWrapperService conversionWrapperService;

    private final List<CommitterConfig.Committer> committers;

    @Transactional
    public void doCommitClaim(String partyId, long claimId, int revision) {
        log.info("Trying to commit and accept claim, partyId='{}', claimId='{}'", partyId, claimId);
        try {
            ClaimModel claimModel = claimManagementService.getClaim(partyId, claimId);

            if (claimModel.getRevision() != revision) {
                throw new InvalidRevisionException(
                        String.format("Invalid claim revision, expected='%s', actual='%s'", claimModel.getRevision(), revision)
                );
            }

            if (claimModel.getClaimStatus().getClaimStatusEnum() != ClaimStatusEnum.pending_acceptance) {
                throw new InvalidClaimStatusException(
                        String.format("Invalid claim status, expected='%s', actual='%s'", ClaimStatusEnum.pending_acceptance, claimModel.getClaimStatus().getClaimStatusEnum()),
                        claimModel.getClaimStatus()
                );
            }

            Claim claim = conversionWrapperService.convertClaim(claimModel);
            committers.forEach(committer -> sendAccept(partyId, claim, committer));
            claimManagementService.acceptClaim(partyId, claimId, revision);
            committers.forEach(committer -> sendCommit(partyId, claim, committer));
            log.info("Claim have been commited and accepted, partyId='{}', claimId='{}'", partyId, claimId);
        } catch (InvalidChangesetException ex) {
            log.warn("An invalid changeset occurred while trying to apply the claim, rollback to pending status", ex);
            claimManagementService.failClaimAcceptance(partyId, claimId, revision);
        } catch (InvalidClaimStatusException | InvalidRevisionException ex) {
            log.warn("Claim has been changed, no commit needed", ex);
        } catch (RuntimeException ex) {
            throw ex;
        }
    }

    public void sendAccept(String partyId, Claim claim, CommitterConfig.Committer committer) {
        log.info("Trying to accept claim in service, serviceId='{}', partyId='{}', claimId='{}'", committer.getId(), partyId, claim.getId());
        try {
            buildClaimCommitterClient(committer).accept(partyId, claim);
            log.info("Claim have been accepted in service, serviceId='{}', claimId='{}'", committer.getId(), claim.getId());
        } catch (InvalidChangeset ex) {
            throw new InvalidChangesetException(
                    String.format("Failed to accept claim in service, serviceId='%s', partyId='%s' claimId='%d'", committer.getId(), partyId, claim.getId()),
                    conversionWrapperService.convertModifications(ex.getInvalidChangeset())
            );
        } catch (TException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void sendCommit(String partyId, Claim claim, CommitterConfig.Committer committer) {
        log.info("Trying to commit claim in service, serviceId='{}', partyId='{}' claimId='{}'", committer.getId(), partyId, claim.getId());
        try {
            buildClaimCommitterClient(committer).commit(partyId, claim);
            log.info("Claim have been commited in service, serviceId='{}', partyId='{}', claimId='{}'", committer.getId(), partyId, claim.getId());
        } catch (TException ex) {
            throw new RuntimeException(ex);
        }
    }

    private ClaimCommitterSrv.Iface buildClaimCommitterClient(CommitterConfig.Committer committer) {
        return new THSpawnClientBuilder()
                .withAddress(committer.getUri())
                .withNetworkTimeout(committer.getTimeout())
                .build(ClaimCommitterSrv.Iface.class);
    }

}
