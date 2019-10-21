package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.LegalEntityModel;
import com.rbkmoney.cm.model.contractor.ContractorLegalEntityCreationModificationModel;
import com.rbkmoney.damsel.domain.LegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class LegalEntityToContractorLegalEntityCreationModificationModelConverter implements ClaimConverter<LegalEntity, ContractorLegalEntityCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorLegalEntityCreationModificationModel convert(LegalEntity legalEntity) {
        ContractorLegalEntityCreationModificationModel contractorLegalEntityCreationModificationModel = new ContractorLegalEntityCreationModificationModel();
        contractorLegalEntityCreationModificationModel.setLegalEntity(conversionService.convert(legalEntity, LegalEntityModel.class));
        return contractorLegalEntityCreationModificationModel;
    }
}
