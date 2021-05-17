package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PayoutToolParamsModel;
import com.rbkmoney.damsel.claim_management.PayoutToolParams;
import com.rbkmoney.damsel.domain.CurrencyRef;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolParamsModelToPayoutToolParamsConverter
        implements ClaimConverter<PayoutToolParamsModel, PayoutToolParams> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolParams convert(PayoutToolParamsModel payoutToolParamsModel) {
        return new PayoutToolParams()
                .setCurrency(new CurrencyRef(payoutToolParamsModel.getCurrencySymbolicCode()))
                .setToolInfo(
                        conversionService.convert(payoutToolParamsModel.getPayoutToolInfo(), PayoutToolInfo.class));
    }
}
