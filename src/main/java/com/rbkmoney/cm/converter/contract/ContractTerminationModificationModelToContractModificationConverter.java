package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractTerminationModificationModel;
import com.rbkmoney.damsel.claim_management.ContractModification;
import com.rbkmoney.damsel.claim_management.ContractTermination;
import org.springframework.stereotype.Component;

@Component
public class ContractTerminationModificationModelToContractModificationConverter
        implements ClaimConverter<ContractTerminationModificationModel, ContractModification> {
    @Override
    public ContractModification convert(ContractTerminationModificationModel contractTerminationModificationModel) {
        return ContractModification.termination(
                new ContractTermination()
                        .setReason(contractTerminationModificationModel.getReason())
        );
    }
}
