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
public class ContractAdjustmentModificationModelToContractAdjustmentModificationUnitConverter implements ClaimConverter<ContractAdjustmentModificationModel, ContractAdjustmentModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractAdjustmentModificationUnit convert(ContractAdjustmentModificationModel contractAdjustmentModificationModel) {
        return new ContractAdjustmentModificationUnit()
                .setAdjustmentId(contractAdjustmentModificationModel.getContractAdjustmentId())
                .setModification(
                        conversionService.convert(contractAdjustmentModificationModel, ContractAdjustmentModification.class)
                );
    }
}
