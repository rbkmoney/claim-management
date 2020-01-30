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
            for (CommitterConfig.Committer committer : committers) {
                sendAccept(partyId, claim, committer);
                sendCommit(partyId, claim, committer);
            }
            claimManagementService.acceptClaim(partyId, claimId, revision);
        } catch (InvalidChangesetException ex) {
            claimManagementService.failClaimAcceptance(partyId, claimId, revision);
        } catch (InvalidClaimStatusException | InvalidRevisionException ex) {
            log.warn("Claim has been changed, no commit needed", ex);
        } catch (RuntimeException ex) {
            throw ex;
        }
    }

    public void sendAccept(String partyId, Claim claim, CommitterConfig.Committer committer) {
        try {
            buildClaimCommitterClient(committer).accept(partyId, claim);
        } catch (InvalidChangeset ex) {
            throw new InvalidChangesetException(conversionWrapperService.convertModifications(ex.getInvalidChangeset()));
        } catch (TException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void sendCommit(String partyId, Claim claim, CommitterConfig.Committer committer) {
        try {
            buildClaimCommitterClient(committer).commit(partyId, claim);
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
