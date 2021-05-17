package com.rbkmoney.cm.repository;

import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.ClaimStatusEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClaimSpecifications {

    public static Specification<ClaimModel> equalsByPartyId(String partyId) {
        return (root, criteriaQuery, criteriaBuilder) -> partyId != null
                ? criteriaBuilder.equal(root.get("partyId"), partyId)
                : criteriaBuilder.conjunction();
    }

    public static Specification<ClaimModel> equalsByClaimId(Long claimId) {
        return (root, criteriaQuery, criteriaBuilder) -> claimId != null
                ? criteriaBuilder.equal(root.get("id"), claimId)
                : criteriaBuilder.conjunction();
    }

    public static Specification<ClaimModel> equalsByPartyIdAndClaimId(String partyId, Long claimId) {
        return equalsByPartyId(partyId).and(equalsByClaimId(claimId));
    }

    public static Specification<ClaimModel> statusIn(List<ClaimStatusEnum> statuses) {
        return (root, criteriaQuery, criteriaBuilder) -> statuses != null && !statuses.isEmpty()
                ? root.get("claimStatus").get("claimStatusEnum").in(statuses)
                : criteriaBuilder.conjunction();
    }

    public static Specification<ClaimModel> equalsByEmail(String email) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(email != null);
            return email != null
                    ? criteriaBuilder.equal(root.join("modifications").get("userInfo").get("email"), email)
                    : criteriaBuilder.conjunction();
        };
    }

    public static Specification<ClaimModel> equalsByPartyIdClaimIdEmailAndStatusIn(String partyId, Long claimId,
                                                                                   String email,
                                                                                   List<ClaimStatusEnum> statuses) {
        return equalsByPartyId(partyId).and(equalsByClaimId(claimId)).and(equalsByEmail(email)).and(statusIn(statuses));
    }

}
