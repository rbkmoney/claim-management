package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractCreationModificationModel;
import com.rbkmoney.cm.model.contractor.ContractorCreationModificationModel;
import com.rbkmoney.cm.model.contractor.ContractorIdentificationLevelModificationModel;
import com.rbkmoney.cm.model.contractor.ContractorModificationModel;
import com.rbkmoney.damsel.claim_management.ContractorModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorModificationToContractorModificationModelConverter implements ClaimConverter<ContractorModification, ContractorModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorModificationModel convert(ContractorModification contractorModification) {
        switch (contractorModification.getSetField()) {
            case CREATION:
                return conversionService.convert(contractorModification.getCreation(), ContractorCreationModificationModel.class);
            case IDENTIFICATION_LEVEL_MODIFICATION:
                return conversionService.convert(contractorModification.getIdentificationLevelModification(), ContractorIdentificationLevelModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", contractorModification.getSetField()));
        }
    }
}
