package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractLegalAgreementBindingModificationModel;
import com.rbkmoney.damsel.claim_management.ContractModification;
import com.rbkmoney.damsel.domain.LegalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractLegalAgreementBindingModificationModelToContractModificationConverter implements ClaimConverter<ContractLegalAgreementBindingModificationModel, ContractModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModification convert(ContractLegalAgreementBindingModificationModel contractLegalAgreementBindingModificationModel) {
        return ContractModification.legal_agreement_binding(
                conversionService.convert(
                        contractLegalAgreementBindingModificationModel.getLegalAgreement(),
                        LegalAgreement.class
                )
        );
    }
}
