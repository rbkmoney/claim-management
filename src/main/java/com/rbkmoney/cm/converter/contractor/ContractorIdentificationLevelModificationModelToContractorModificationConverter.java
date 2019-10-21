package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contractor.ContractorIdentificationLevelModificationModel;
import com.rbkmoney.damsel.claim_management.ContractorModification;
import com.rbkmoney.damsel.domain.ContractorIdentificationLevel;
import org.springframework.stereotype.Component;

@Component
public class ContractorIdentificationLevelModificationModelToContractorModificationConverter implements ClaimConverter<ContractorIdentificationLevelModificationModel, ContractorModification> {

    @Override
    public ContractorModification convert(ContractorIdentificationLevelModificationModel contractorIdentificationLevelModificationModel) {
        return ContractorModification.identification_level_modification(
                ContractorIdentificationLevel.findByValue(
                        contractorIdentificationLevelModificationModel
                                .getContractorIdentificationLevel()
                                .getLevel()
                )
        );
    }
}
