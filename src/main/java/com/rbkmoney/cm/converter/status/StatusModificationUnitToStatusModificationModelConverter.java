package com.rbkmoney.cm.converter.status;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ClaimStatusModel;
import com.rbkmoney.cm.model.status.StatusModificationModel;
import com.rbkmoney.cm.model.status.StatusModificationTypeEnum;
import com.rbkmoney.damsel.claim_management.StatusModificationUnit;
import com.rbkmoney.geck.common.util.TBaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class StatusModificationUnitToStatusModificationModelConverter implements ClaimConverter<StatusModificationUnit, StatusModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public StatusModificationModel convert(StatusModificationUnit statusModificationUnit) {
        StatusModificationModel statusModificationModel = new StatusModificationModel();
        statusModificationModel.setClaimStatus(
                conversionService.convert(statusModificationUnit.getStatus(), ClaimStatusModel.class)
        );
        statusModificationModel.setStatusModificationType(
                TBaseUtil.unionFieldToEnum(
                        statusModificationUnit.getModification(),
                        StatusModificationTypeEnum.class
                )
        );
        return statusModificationModel;
    }
}
