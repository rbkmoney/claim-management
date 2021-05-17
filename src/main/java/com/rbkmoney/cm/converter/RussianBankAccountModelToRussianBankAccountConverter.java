package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.RussianBankAccountModel;
import com.rbkmoney.damsel.domain.RussianBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RussianBankAccountModelToRussianBankAccountConverter
        implements ClaimConverter<RussianBankAccountModel, RussianBankAccount> {

    @Override
    public RussianBankAccount convert(RussianBankAccountModel russianBankAccountModel) {
        return new RussianBankAccount()
                .setAccount(russianBankAccountModel.getAccount())
                .setBankBik(russianBankAccountModel.getBankBik())
                .setBankName(russianBankAccountModel.getBankName())
                .setBankPostAccount(russianBankAccountModel.getBankPostAccount())
                .setAccount(russianBankAccountModel.getAccount());
    }
}
