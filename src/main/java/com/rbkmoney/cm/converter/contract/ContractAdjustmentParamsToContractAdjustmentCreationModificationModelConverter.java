package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ContractAdjustmentParamsModel;
import com.rbkmoney.cm.model.contract.ContractAdjustmentCreationModificationModel;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentParamsToContractAdjustmentCreationModificationModelConverter implements ClaimConverter<ContractAdjustmentParams, ContractAdjustmentCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractAdjustmentCreationModificationModel convert(ContractAdjustmentParams contractAdjustmentParams) {
        ContractAdjustmentCreationModificationModel contractAdjustmentCreationModificationModel = new ContractAdjustmentCreationModificationModel();
        contractAdjustmentCreationModificationModel.setContractAdjustmentParams(conversionService.convert(contractAdjustmentParams, ContractAdjustmentParamsModel.class));
        return contractAdjustmentCreationModificationModel;
    }
}
