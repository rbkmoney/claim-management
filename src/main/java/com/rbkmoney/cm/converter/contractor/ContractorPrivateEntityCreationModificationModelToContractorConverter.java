package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contractor.ContractorPrivateEntityCreationModificationModel;
import com.rbkmoney.damsel.domain.Contractor;
import com.rbkmoney.damsel.domain.PrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorPrivateEntityCreationModificationModelToContractorConverter implements ClaimConverter<ContractorPrivateEntityCreationModificationModel, Contractor> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Contractor convert(ContractorPrivateEntityCreationModificationModel contractorPrivateEntityCreationModificationModel) {
        return Contractor.private_entity(
                conversionService.convert(
                        contractorPrivateEntityCreationModificationModel.getPrivateEntity(),
                        PrivateEntity.class
                )
        );
    }
}
