package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ContractAdjustmentParamsModel;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentParams;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentParamsToContractAdjustmentParamsModelConverter
        implements ClaimConverter<ContractAdjustmentParams, ContractAdjustmentParamsModel> {
    @Override
    public ContractAdjustmentParamsModel convert(ContractAdjustmentParams contractAdjustmentParams) {
        ContractAdjustmentParamsModel contractAdjustmentParamsModel = new ContractAdjustmentParamsModel();
        contractAdjustmentParamsModel.setContractTemplateId(contractAdjustmentParams.getTemplate().getId());
        return contractAdjustmentParamsModel;
    }
}
