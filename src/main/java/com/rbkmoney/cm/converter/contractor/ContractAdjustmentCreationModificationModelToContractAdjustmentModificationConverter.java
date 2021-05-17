package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractAdjustmentCreationModificationModel;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentModification;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentCreationModificationModelToContractAdjustmentModificationConverter
        implements ClaimConverter<ContractAdjustmentCreationModificationModel, ContractAdjustmentModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractAdjustmentModification convert(
            ContractAdjustmentCreationModificationModel contractAdjustmentCreationModificationModel) {
        return ContractAdjustmentModification.creation(
                conversionService.convert(contractAdjustmentCreationModificationModel.getContractAdjustmentParams(),
                        ContractAdjustmentParams.class)
        );
    }
}
