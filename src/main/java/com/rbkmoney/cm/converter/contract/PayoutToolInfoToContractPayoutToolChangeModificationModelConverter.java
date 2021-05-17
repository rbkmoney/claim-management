package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.PayoutToolInfoModel;
import com.rbkmoney.cm.model.contract.ContractPayoutToolChangeModificationModel;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoToContractPayoutToolChangeModificationModelConverter
        implements ClaimConverter<PayoutToolInfo, ContractPayoutToolChangeModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractPayoutToolChangeModificationModel convert(PayoutToolInfo payoutToolInfo) {
        ContractPayoutToolChangeModificationModel payoutToolInfoChangeModificationModel =
                new ContractPayoutToolChangeModificationModel();
        payoutToolInfoChangeModificationModel
                .setPayoutToolInfoModel(conversionService.convert(payoutToolInfo, PayoutToolInfoModel.class));
        return payoutToolInfoChangeModificationModel;
    }
}
