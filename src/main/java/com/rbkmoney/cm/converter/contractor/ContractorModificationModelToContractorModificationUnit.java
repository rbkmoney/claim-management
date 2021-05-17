package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contractor.ContractorModificationModel;
import com.rbkmoney.damsel.claim_management.ContractorModification;
import com.rbkmoney.damsel.claim_management.ContractorModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorModificationModelToContractorModificationUnit
        implements ClaimConverter<ContractorModificationModel, ContractorModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorModificationUnit convert(ContractorModificationModel contractorModificationModel) {
        return new ContractorModificationUnit()
                .setId(contractorModificationModel.getContractorId())
                .setModification(
                        conversionService.convert(contractorModificationModel, ContractorModification.class)
                );
    }
}
