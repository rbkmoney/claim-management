package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.InternationalBankAccountModel;
import com.rbkmoney.cm.model.PayoutToolInfoInternationalBankAccountModel;
import com.rbkmoney.damsel.domain.InternationalBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class InternationalBankAccountToPayoutToolInfoInternationalBankAccountModelConverter
        implements ClaimConverter<InternationalBankAccount, PayoutToolInfoInternationalBankAccountModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolInfoInternationalBankAccountModel convert(InternationalBankAccount internationalBankAccount) {
        PayoutToolInfoInternationalBankAccountModel payoutToolInfoInternationalBankAccountModel =
                new PayoutToolInfoInternationalBankAccountModel();
        payoutToolInfoInternationalBankAccountModel.setInternationalBankAccountModel(
                conversionService.convert(internationalBankAccount, InternationalBankAccountModel.class));
        return payoutToolInfoInternationalBankAccountModel;
    }
}
