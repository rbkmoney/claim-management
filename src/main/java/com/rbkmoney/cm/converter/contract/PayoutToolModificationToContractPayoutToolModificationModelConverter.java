package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractPayoutToolChangeModificationModel;
import com.rbkmoney.cm.model.contract.ContractPayoutToolCreationModificationModel;
import com.rbkmoney.cm.model.contract.ContractPayoutToolModificationModel;
import com.rbkmoney.damsel.claim_management.PayoutToolModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolModificationToContractPayoutToolModificationModelConverter implements ClaimConverter<PayoutToolModification, ContractPayoutToolModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractPayoutToolModificationModel convert(PayoutToolModification payoutToolModification) {
        switch (payoutToolModification.getSetField()) {
            case CREATION:
                return conversionService.convert(payoutToolModification.getCreation(), ContractPayoutToolCreationModificationModel.class);
            case INFO_MODIFICATION:
                return conversionService.convert(payoutToolModification.getInfoModification(), ContractPayoutToolChangeModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", payoutToolModification.getSetField()));
        }
    }
}
