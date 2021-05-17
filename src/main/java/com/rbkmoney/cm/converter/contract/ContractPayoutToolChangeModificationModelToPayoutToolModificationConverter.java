package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractPayoutToolChangeModificationModel;
import com.rbkmoney.damsel.claim_management.PayoutToolModification;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractPayoutToolChangeModificationModelToPayoutToolModificationConverter
        implements ClaimConverter<ContractPayoutToolChangeModificationModel, PayoutToolModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolModification convert(
            ContractPayoutToolChangeModificationModel contractPayoutToolChangeModificationModel) {
        return PayoutToolModification.info_modification(
                conversionService.convert(contractPayoutToolChangeModificationModel.getPayoutToolInfoModel(),
                        PayoutToolInfo.class)
        );
    }
}
