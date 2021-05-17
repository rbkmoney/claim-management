package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractPayoutToolModificationModel;
import com.rbkmoney.damsel.claim_management.PayoutToolModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolModificationUnitToContractPayoutToolModificationModelConverter
        implements ClaimConverter<PayoutToolModificationUnit, ContractPayoutToolModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractPayoutToolModificationModel convert(PayoutToolModificationUnit payoutToolModificationUnit) {
        ContractPayoutToolModificationModel payoutToolModificationModel = conversionService
                .convert(payoutToolModificationUnit.getModification(), ContractPayoutToolModificationModel.class);
        payoutToolModificationModel.setPayoutToolId(payoutToolModificationUnit.getPayoutToolId());
        return payoutToolModificationModel;
    }
}
