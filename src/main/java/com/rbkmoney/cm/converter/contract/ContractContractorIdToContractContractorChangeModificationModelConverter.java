package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractContractorChangeModificationModel;
import org.springframework.stereotype.Component;

@Component
public class ContractContractorIdToContractContractorChangeModificationModelConverter
        implements ClaimConverter<String, ContractContractorChangeModificationModel> {

    @Override
    public ContractContractorChangeModificationModel convert(String contractorId) {
        ContractContractorChangeModificationModel contractContractorChangeModificationModel =
                new ContractContractorChangeModificationModel();
        contractContractorChangeModificationModel.setContractorId(contractorId);
        return contractContractorChangeModificationModel;
    }

}
