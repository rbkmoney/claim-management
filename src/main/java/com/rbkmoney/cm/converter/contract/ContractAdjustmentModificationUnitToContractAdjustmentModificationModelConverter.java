package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractAdjustmentModificationModel;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentModification;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentModificationUnitToContractAdjustmentModificationModelConverter
        implements ClaimConverter<ContractAdjustmentModificationUnit, ContractAdjustmentModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractAdjustmentModificationModel convert(
            ContractAdjustmentModificationUnit contractAdjustmentModificationUnit) {
        ContractAdjustmentModification contractAdjustmentModification =
                contractAdjustmentModificationUnit.getModification();
        ContractAdjustmentModificationModel contractAdjustmentModificationModel =
                conversionService.convert(contractAdjustmentModification, ContractAdjustmentModificationModel.class);
        if (contractAdjustmentModificationModel == null) {
            throw new IllegalStateException("ContractAdjustmentModificationModel can't be null");
        }
        contractAdjustmentModificationModel
                .setContractAdjustmentId(contractAdjustmentModificationUnit.getAdjustmentId());
        return contractAdjustmentModificationModel;
    }
}
