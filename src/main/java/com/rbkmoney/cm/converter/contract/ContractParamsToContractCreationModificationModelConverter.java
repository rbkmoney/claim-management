package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ContractParamsModel;
import com.rbkmoney.cm.model.contract.ContractCreationModificationModel;
import com.rbkmoney.damsel.claim_management.ContractParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractParamsToContractCreationModificationModelConverter implements ClaimConverter<ContractParams, ContractCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractCreationModificationModel convert(ContractParams contractParams) {
        ContractCreationModificationModel contractCreationModificationModel = new ContractCreationModificationModel();
        contractCreationModificationModel.setContractParams(conversionService.convert(contractParams, ContractParamsModel.class));
        return contractCreationModificationModel;
    }
}
