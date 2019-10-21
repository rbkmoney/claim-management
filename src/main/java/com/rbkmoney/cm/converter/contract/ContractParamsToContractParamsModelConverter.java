package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ContractParamsModel;
import com.rbkmoney.damsel.claim_management.ContractParams;
import org.springframework.stereotype.Component;

@Component
public class ContractParamsToContractParamsModelConverter implements ClaimConverter<ContractParams, ContractParamsModel> {
    @Override
    public ContractParamsModel convert(ContractParams contractParams) {
        ContractParamsModel contractParamsModel = new ContractParamsModel();
        contractParamsModel.setContractorId(contractParams.getContractorId());
        if (contractParams.isSetTemplate()) {
            contractParamsModel.setContractTemplateId(contractParams.getTemplate().getId());
        }
        if (contractParams.isSetPaymentInstitution()) {
            contractParamsModel.setPaymentInstitutionId(contractParams.getPaymentInstitution().getId());
        }
        return contractParamsModel;
    }
}
