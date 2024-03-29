package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractModificationModel;
import com.rbkmoney.damsel.claim_management.ContractModificationUnit;
import com.rbkmoney.damsel.claim_management.PartyModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractModificationModelToPartyModificationConverter
        implements ClaimConverter<ContractModificationModel, PartyModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModification convert(ContractModificationModel contractModificationModel) {
        return PartyModification.contract_modification(
                conversionService.convert(contractModificationModel, ContractModificationUnit.class)
        );
    }
}
