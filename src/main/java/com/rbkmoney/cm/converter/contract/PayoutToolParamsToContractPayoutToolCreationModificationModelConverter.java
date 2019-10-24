package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.PayoutToolParamsModel;
import com.rbkmoney.cm.model.contract.ContractPayoutToolCreationModificationModel;
import com.rbkmoney.damsel.claim_management.PayoutToolParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolParamsToContractPayoutToolCreationModificationModelConverter implements ClaimConverter<PayoutToolParams, ContractPayoutToolCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractPayoutToolCreationModificationModel convert(PayoutToolParams payoutToolParams) {
        ContractPayoutToolCreationModificationModel payoutToolCreationModificationModel = new ContractPayoutToolCreationModificationModel();
        payoutToolCreationModificationModel.setPayoutToolParams(conversionService.convert(payoutToolParams, PayoutToolParamsModel.class));
        return payoutToolCreationModificationModel;
    }
}
