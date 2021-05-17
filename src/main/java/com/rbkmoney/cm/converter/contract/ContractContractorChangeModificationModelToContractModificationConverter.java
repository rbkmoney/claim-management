package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractContractorChangeModificationModel;
import com.rbkmoney.damsel.claim_management.ContractModification;
import org.springframework.stereotype.Component;

@Component
public class ContractContractorChangeModificationModelToContractModificationConverter
        implements ClaimConverter<ContractContractorChangeModificationModel, ContractModification> {

    @Override
    public ContractModification convert(
            ContractContractorChangeModificationModel contractContractorChangeModificationModel) {
        return ContractModification.contractor_modification(
                contractContractorChangeModificationModel.getContractorId()
        );
    }
}
