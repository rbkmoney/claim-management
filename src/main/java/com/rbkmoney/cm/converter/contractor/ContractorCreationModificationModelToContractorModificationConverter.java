package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contractor.ContractorCreationModificationModel;
import com.rbkmoney.damsel.claim_management.ContractorModification;
import com.rbkmoney.damsel.domain.Contractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorCreationModificationModelToContractorModificationConverter
        implements ClaimConverter<ContractorCreationModificationModel, ContractorModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorModification convert(ContractorCreationModificationModel contractorCreationModificationModel) {
        return ContractorModification.creation(
                conversionService.convert(contractorCreationModificationModel, Contractor.class)
        );
    }
}
