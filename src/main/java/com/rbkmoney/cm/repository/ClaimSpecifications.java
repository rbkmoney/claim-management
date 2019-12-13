package com.rbkmoney.cm.repository;

import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.ClaimStatusEnum;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ClaimSpecifications {

    public static Specification<ClaimModel> equalsByPartyId(String partyId) {
        return (Specification<ClaimModel>) (root, criteriaQuery, criteriaBuilder) -> partyId != null
                ? criteriaBuilder.equal(root.get("partyId"), partyId)
                : criteriaBuilder.conjunction();
    }

    public static Specification<ClaimModel> equalsByClaimId(Long claimId) {
        return (Specification<ClaimModel>) (root, criteriaQuery, criteriaBuilder) -> claimId != null
                ? criteriaBuilder.equal(root.get("id"), claimId)
                : criteriaBuilder.conjunction();
    }

    public static Specification<ClaimModel> equalsByPartyIdAndClaimId(String partyId, Long claimId) {
        return equalsByPartyId(partyId).and(equalsByClaimId(claimId));
    }

    public static Specification<ClaimModel> statusIn(List<ClaimStatusEnum> statuses) {
        return (Specification<ClaimModel>) (root, criteriaQuery, criteriaBuilder) -> statuses != null && !statuses.isEmpty()
                ? root.get("claimStatus").get("claimStatusEnum").in(statuses)
                : criteriaBuilder.conjunction();
    }

    public static Specification<ClaimModel> equalsByPartyIdClaimIdAndStatusIn(String partyId, Long claimId, List<ClaimStatusEnum> statuses) {
        return equalsByPartyId(partyId).and(equalsByClaimId(claimId)).and(statusIn(statuses));
    }

}
