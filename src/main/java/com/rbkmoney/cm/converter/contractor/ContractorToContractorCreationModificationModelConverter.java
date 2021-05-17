package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contractor.ContractorCreationModificationModel;
import com.rbkmoney.cm.model.contractor.ContractorLegalEntityCreationModificationModel;
import com.rbkmoney.cm.model.contractor.ContractorPrivateEntityCreationModificationModel;
import com.rbkmoney.cm.model.contractor.ContractorRegisteredUserCreationModificationModel;
import com.rbkmoney.damsel.domain.Contractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorToContractorCreationModificationModelConverter
        implements ClaimConverter<Contractor, ContractorCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorCreationModificationModel convert(Contractor contractor) {
        switch (contractor.getSetField()) {
            case LEGAL_ENTITY:
                return conversionService
                        .convert(contractor.getLegalEntity(), ContractorLegalEntityCreationModificationModel.class);
            case PRIVATE_ENTITY:
                return conversionService
                        .convert(contractor.getPrivateEntity(), ContractorPrivateEntityCreationModificationModel.class);
            case REGISTERED_USER:
                return conversionService.convert(contractor.getRegisteredUser(),
                        ContractorRegisteredUserCreationModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", contractor.getSetField()));
        }
    }
}
