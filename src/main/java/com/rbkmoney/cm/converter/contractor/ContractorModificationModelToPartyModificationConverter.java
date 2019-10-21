package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contractor.ContractorModificationModel;
import com.rbkmoney.damsel.claim_management.ContractorModificationUnit;
import com.rbkmoney.damsel.claim_management.PartyModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorModificationModelToPartyModificationConverter implements ClaimConverter<ContractorModificationModel, PartyModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModification convert(ContractorModificationModel contractorModificationModel) {
        return PartyModification.contractor_modification(
                conversionService.convert(contractorModificationModel, ContractorModificationUnit.class)
        );
    }
}
