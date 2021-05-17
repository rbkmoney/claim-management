package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PayoutToolInfoRussianBankAccountModel;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import com.rbkmoney.damsel.domain.RussianBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoRussianBankAccountModelToPayoutToolInfoConverter
        implements ClaimConverter<PayoutToolInfoRussianBankAccountModel, PayoutToolInfo> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolInfo convert(PayoutToolInfoRussianBankAccountModel payoutToolInfoRussianBankAccountModel) {
        return PayoutToolInfo.russian_bank_account(
                conversionService.convert(payoutToolInfoRussianBankAccountModel.getRussianBankAccount(),
                        RussianBankAccount.class)
        );
    }
}
