package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PayoutToolInfoPaymentInstitutionAccountModel;
import com.rbkmoney.damsel.domain.PaymentInstitutionAccount;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoPaymentInstitutionAccountModelToPayoutToolInfoConverter
        implements ClaimConverter<PayoutToolInfoPaymentInstitutionAccountModel, PayoutToolInfo> {

    @Override
    public PayoutToolInfo convert(PayoutToolInfoPaymentInstitutionAccountModel payoutToolInfoPaymentInstitutionAccountModel) {
        return PayoutToolInfo.payment_institution_account(new PaymentInstitutionAccount());
    }
}
