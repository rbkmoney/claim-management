package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.LegalAgreementModel;
import com.rbkmoney.cm.model.contract.ContractLegalAgreementBindingModificationModel;
import com.rbkmoney.damsel.domain.LegalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class LegalAgreementToContractLegalAgreementBindingModificationModelConverter implements ClaimConverter<LegalAgreement, ContractLegalAgreementBindingModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractLegalAgreementBindingModificationModel convert(LegalAgreement legalAgreement) {
        ContractLegalAgreementBindingModificationModel contractLegalAgreementBindingModificationModel = new ContractLegalAgreementBindingModificationModel();
        contractLegalAgreementBindingModificationModel.setLegalAgreement(conversionService.convert(legalAgreement, LegalAgreementModel.class));
        return contractLegalAgreementBindingModificationModel;
    }
}
