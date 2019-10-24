package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ClaimStatusEnum;
import com.rbkmoney.cm.model.ClaimStatusModel;
import com.rbkmoney.damsel.claim_management.ClaimStatus;
import com.rbkmoney.geck.common.util.TBaseUtil;
import org.springframework.stereotype.Component;

@Component
public class ClaimStatusToClaimStatusModelConverter implements ClaimConverter<ClaimStatus, ClaimStatusModel> {
    @Override
    public ClaimStatusModel convert(ClaimStatus claimStatus) {
        ClaimStatusModel claimStatusModel = new ClaimStatusModel();
        claimStatusModel.setClaimStatusEnum(TBaseUtil.unionFieldToEnum(claimStatus, ClaimStatusEnum.class));
        claimStatusModel.setClaimStatusReason(extractReason(claimStatus));
        return claimStatusModel;
    }

    private String extractReason(ClaimStatus claimStatus) {
        switch (claimStatus.getSetField()) {
            case DENIED:
                return claimStatus.getDenied().getReason();
            case REVOKED:
                return claimStatus.getRevoked().getReason();
            default:
                return null;
        }
    }
}
