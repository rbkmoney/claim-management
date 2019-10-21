package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ContractAdjustmentParamsModel;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentParams;
import com.rbkmoney.damsel.domain.ContractTemplateRef;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentParamsModelToContractAdjustmentParamsConverter implements ClaimConverter<ContractAdjustmentParamsModel, ContractAdjustmentParams> {
    @Override
    public ContractAdjustmentParams convert(ContractAdjustmentParamsModel contractAdjustmentParamsModel) {
        return new ContractAdjustmentParams()
                .setTemplate(new ContractTemplateRef(contractAdjustmentParamsModel.getContractTemplateId()));
    }
}
