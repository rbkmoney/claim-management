package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ContractParamsModel;
import com.rbkmoney.damsel.claim_management.ContractParams;
import com.rbkmoney.damsel.domain.ContractTemplateRef;
import com.rbkmoney.damsel.domain.PaymentInstitutionRef;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContractParamsModelToContractParamsConverter
        implements ClaimConverter<ContractParamsModel, ContractParams> {
    @Override
    public ContractParams convert(ContractParamsModel contractParamsModel) {
        return new ContractParams()
                .setContractorId(contractParamsModel.getContractorId())
                .setTemplate(
                        Optional.ofNullable(contractParamsModel.getContractTemplateId())
                                .map(ContractTemplateRef::new)
                                .orElse(null)
                )
                .setPaymentInstitution(
                        Optional.ofNullable(contractParamsModel.getPaymentInstitutionId())
                                .map(PaymentInstitutionRef::new)
                                .orElse(null)
                );
    }
}
