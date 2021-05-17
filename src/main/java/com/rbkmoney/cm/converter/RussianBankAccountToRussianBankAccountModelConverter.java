package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.RussianBankAccountModel;
import com.rbkmoney.damsel.domain.RussianBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RussianBankAccountToRussianBankAccountModelConverter
        implements ClaimConverter<RussianBankAccount, RussianBankAccountModel> {
    @Override
    public RussianBankAccountModel convert(RussianBankAccount russianBankAccount) {
        RussianBankAccountModel russianBankAccountModel = new RussianBankAccountModel();
        russianBankAccountModel.setAccount(russianBankAccount.getAccount());
        russianBankAccountModel.setBankPostAccount(russianBankAccount.getBankPostAccount());
        russianBankAccountModel.setBankBik(russianBankAccount.getBankBik());
        russianBankAccountModel.setBankName(russianBankAccount.getBankName());
        return russianBankAccountModel;
    }
}
