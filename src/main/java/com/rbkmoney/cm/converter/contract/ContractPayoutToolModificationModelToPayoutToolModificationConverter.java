package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractPayoutToolModificationModel;
import com.rbkmoney.damsel.claim_management.PayoutToolModification;
import com.rbkmoney.damsel.claim_management.PayoutToolModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractPayoutToolModificationModelToPayoutToolModificationConverter implements ClaimConverter<ContractPayoutToolModificationModel, PayoutToolModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolModificationUnit convert(ContractPayoutToolModificationModel contractPayoutToolModificationModel) {
        return new PayoutToolModificationUnit()
                .setPayoutToolId(contractPayoutToolModificationModel.getPayoutToolId())
                .setModification(
                        conversionService.convert(contractPayoutToolModificationModel, PayoutToolModification.class)
                );
    }
}
