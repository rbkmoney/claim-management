package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PayoutToolInfoModel;
import com.rbkmoney.cm.model.PayoutToolParamsModel;
import com.rbkmoney.damsel.claim_management.PayoutToolParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolParamsToPayoutToolParamsModelConverter
        implements ClaimConverter<PayoutToolParams, PayoutToolParamsModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolParamsModel convert(PayoutToolParams payoutToolParams) {
        PayoutToolParamsModel payoutToolParamsModel = new PayoutToolParamsModel();
        payoutToolParamsModel.setCurrencySymbolicCode(payoutToolParams.getCurrency().getSymbolicCode());

        payoutToolParamsModel.setPayoutToolInfo(
                conversionService.convert(payoutToolParams.getToolInfo(), PayoutToolInfoModel.class));

        return payoutToolParamsModel;
    }
}
