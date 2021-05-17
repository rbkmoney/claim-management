package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contractor.ContractorRegisteredUserCreationModificationModel;
import com.rbkmoney.damsel.domain.RegisteredUser;
import org.springframework.stereotype.Component;

@Component
public class RegisteredUserToContractorRegisteredUserCreationModificationModelConverter
        implements ClaimConverter<RegisteredUser, ContractorRegisteredUserCreationModificationModel> {
    @Override
    public ContractorRegisteredUserCreationModificationModel convert(RegisteredUser registeredUser) {
        ContractorRegisteredUserCreationModificationModel contractorRegisteredUserCreationModificationModel =
                new ContractorRegisteredUserCreationModificationModel();
        contractorRegisteredUserCreationModificationModel.setEmail(registeredUser.getEmail());
        return contractorRegisteredUserCreationModificationModel;
    }
}
