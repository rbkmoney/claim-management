package com.rbkmoney.cm.service;

import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.ClaimStatusEnum;
import com.rbkmoney.cm.model.ClaimStatusModel;
import com.rbkmoney.cm.model.MetadataModel;
import com.rbkmoney.cm.pageable.ClaimPageRequest;
import com.rbkmoney.cm.pageable.ClaimPageResponse;
import com.rbkmoney.damsel.claim_management.Claim;
import com.rbkmoney.damsel.claim_management.Modification;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClaimManagementService {

    Claim createClaim(String partyId, List<Modification> changeset);

    void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset);

    ClaimModel getClaim(String partyId, long claimId);

    ClaimModel acceptClaim(String partyId, long claimId, int revision);

    ClaimModel revokeClaim(String partyId, long claimId, int revision, String reason);

    ClaimModel denyClaim(String partyId, long claimId, int revision, String reason);

    ClaimModel requestClaimReview(String partyId, long claimId, int revision);

    ClaimModel requestClaimChanges(String partyId, long claimId, int revision);

    ClaimModel changeStatus(String partyId, long claimId, int revision, ClaimStatusModel targetClaimStatus, List<ClaimStatusEnum> expectedStatuses);

    ClaimPageResponse searchClaims(String partyId, Long claimId, List<ClaimStatusEnum> statuses, String continuationToken, int limit);

    Page<ClaimModel> searchClaims(String partyId, Long claimId, List<ClaimStatusEnum> statuses, ClaimPageRequest claimPageRequest);

    MetadataModel getMetadata(String partyId, long claimId, String key);

    void setMetadata(String partyId, long claimId, String key, MetadataModel metadataModel);

    void removeMetadata(String partyId, long claimId, String key);

}
