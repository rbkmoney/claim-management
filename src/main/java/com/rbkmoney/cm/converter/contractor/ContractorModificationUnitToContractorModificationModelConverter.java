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
public class ContractorModificationUnitToContractorModificationModelConverter
        implements ClaimConverter<ContractorModificationUnit, ContractorModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorModificationModel convert(ContractorModificationUnit contractorModificationUnit) {
        ContractorModification contractorModification = contractorModificationUnit.getModification();
        ContractorModificationModel contractorModificationModel =
                conversionService.convert(contractorModification, ContractorModificationModel.class);
        if (contractorModificationModel == null) {
            throw new IllegalStateException("ContractorModificationModel can't be null");
        }
        contractorModificationModel.setContractorId(contractorModificationUnit.getId());
        return contractorModificationModel;
    }
}
