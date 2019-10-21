package com.rbkmoney.cm.converter.contractor;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contractor.ContractorIdentificationLevelModificationModel;
import com.rbkmoney.damsel.domain.ContractorIdentificationLevel;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.springframework.stereotype.Component;

@Component
public class ContractorIdentificationLevelToContractorIdentificationLevelModificationModelConverter implements ClaimConverter<ContractorIdentificationLevel, ContractorIdentificationLevelModificationModel> {
    @Override
    public ContractorIdentificationLevelModificationModel convert(ContractorIdentificationLevel contractorIdentificationLevel) {
        ContractorIdentificationLevelModificationModel contractorIdentificationLevelModificationModel = new ContractorIdentificationLevelModificationModel();
        contractorIdentificationLevelModificationModel.setContractorIdentificationLevel(
                TypeUtil.toEnumField(
                        contractorIdentificationLevel.name(),
                        com.rbkmoney.cm.model.contractor.ContractorIdentificationLevel.class
                )
        );
        return contractorIdentificationLevelModificationModel;
    }
}
