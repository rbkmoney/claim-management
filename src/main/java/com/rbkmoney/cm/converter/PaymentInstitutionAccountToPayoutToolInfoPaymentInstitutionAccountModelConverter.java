package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PayoutToolInfoPaymentInstitutionAccountModel;
import com.rbkmoney.damsel.domain.PaymentInstitutionAccount;
import org.springframework.stereotype.Component;

@Component
public class PaymentInstitutionAccountToPayoutToolInfoPaymentInstitutionAccountModelConverter
        implements ClaimConverter<PaymentInstitutionAccount, PayoutToolInfoPaymentInstitutionAccountModel> {

    @Override
    public PayoutToolInfoPaymentInstitutionAccountModel convert(PaymentInstitutionAccount paymentInstitutionAccount) {
        PayoutToolInfoPaymentInstitutionAccountModel payoutToolInfoRussianBankAccountModel =
                new PayoutToolInfoPaymentInstitutionAccountModel();
        return payoutToolInfoRussianBankAccountModel;
    }
}
