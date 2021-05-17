package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.PrivateEntityModel;
import com.rbkmoney.cm.model.contractor.ContractorPrivateEntityCreationModificationModel;
import com.rbkmoney.damsel.domain.PrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PrivateEntityToContractorPrivateEntityCreationModificationModelConverter
        implements ClaimConverter<PrivateEntity, ContractorPrivateEntityCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorPrivateEntityCreationModificationModel convert(PrivateEntity privateEntity) {
        ContractorPrivateEntityCreationModificationModel contractorPrivateEntityCreationModificationModel =
                new ContractorPrivateEntityCreationModificationModel();
        contractorPrivateEntityCreationModificationModel
                .setPrivateEntity(conversionService.convert(privateEntity, PrivateEntityModel.class));
        return contractorPrivateEntityCreationModificationModel;
    }
}
