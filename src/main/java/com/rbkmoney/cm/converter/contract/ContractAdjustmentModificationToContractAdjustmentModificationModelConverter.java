package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractAdjustmentCreationModificationModel;
import com.rbkmoney.cm.model.contract.ContractAdjustmentModificationModel;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentModificationToContractAdjustmentModificationModelConverter implements ClaimConverter<ContractAdjustmentModification, ContractAdjustmentModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractAdjustmentModificationModel convert(ContractAdjustmentModification contractAdjustmentModification) {
        switch (contractAdjustmentModification.getSetField()) {
            case CREATION:
                return conversionService.convert(contractAdjustmentModification.getCreation(), ContractAdjustmentCreationModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", contractAdjustmentModification.getSetField()));
        }
    }
}
