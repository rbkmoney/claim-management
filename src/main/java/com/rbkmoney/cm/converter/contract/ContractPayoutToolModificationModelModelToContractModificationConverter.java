package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractPayoutToolModificationModel;
import com.rbkmoney.damsel.claim_management.ContractModification;
import com.rbkmoney.damsel.claim_management.PayoutToolModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractPayoutToolModificationModelModelToContractModificationConverter
        implements ClaimConverter<ContractPayoutToolModificationModel, ContractModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModification convert(ContractPayoutToolModificationModel contractPayoutToolModificationModel) {
        return ContractModification.payout_tool_modification(
                conversionService.convert(contractPayoutToolModificationModel, PayoutToolModificationUnit.class)
        );
    }
}
