package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PayoutToolInfoRussianBankAccountModel;
import com.rbkmoney.cm.model.RussianBankAccountModel;
import com.rbkmoney.damsel.domain.RussianBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianBankAccountToPayoutToolInfoRussianBankAccountModelConverter
        implements ClaimConverter<RussianBankAccount, PayoutToolInfoRussianBankAccountModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolInfoRussianBankAccountModel convert(RussianBankAccount russianBankAccount) {
        PayoutToolInfoRussianBankAccountModel payoutToolInfoRussianBankAccountModel =
                new PayoutToolInfoRussianBankAccountModel();
        payoutToolInfoRussianBankAccountModel
                .setRussianBankAccount(conversionService.convert(russianBankAccount, RussianBankAccountModel.class));
        return payoutToolInfoRussianBankAccountModel;
    }
}
