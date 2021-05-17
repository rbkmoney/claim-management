package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractTerminationModificationModel;
import com.rbkmoney.damsel.claim_management.ContractTermination;
import org.springframework.stereotype.Component;

@Component
public class ContractTerminationToContractTerminationModificationModelConverter
        implements ClaimConverter<ContractTermination, ContractTerminationModificationModel> {

    @Override
    public ContractTerminationModificationModel convert(ContractTermination contractTermination) {
        ContractTerminationModificationModel contractTerminationModificationModel =
                new ContractTerminationModificationModel();
        contractTerminationModificationModel.setReason(contractTermination.getReason());
        return contractTerminationModificationModel;
    }

}
