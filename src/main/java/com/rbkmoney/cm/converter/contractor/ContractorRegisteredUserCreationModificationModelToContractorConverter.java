package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contractor.ContractorRegisteredUserCreationModificationModel;
import com.rbkmoney.damsel.domain.Contractor;
import com.rbkmoney.damsel.domain.RegisteredUser;
import org.springframework.stereotype.Component;

@Component
public class ContractorRegisteredUserCreationModificationModelToContractorConverter implements ClaimConverter<ContractorRegisteredUserCreationModificationModel, Contractor> {
    @Override
    public Contractor convert(ContractorRegisteredUserCreationModificationModel contractorRegisteredUserCreationModificationModel) {
        return Contractor.registered_user(
                new RegisteredUser()
                        .setEmail(contractorRegisteredUserCreationModificationModel.getEmail())
        );
    }
}
