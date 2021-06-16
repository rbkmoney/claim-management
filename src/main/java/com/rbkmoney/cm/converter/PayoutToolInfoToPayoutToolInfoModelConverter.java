package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.*;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoToPayoutToolInfoModelConverter
        implements ClaimConverter<PayoutToolInfo, PayoutToolInfoModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolInfoModel convert(PayoutToolInfo payoutToolInfo) {
        switch (payoutToolInfo.getSetField()) {
            case RUSSIAN_BANK_ACCOUNT:
                return conversionService
                        .convert(payoutToolInfo.getRussianBankAccount(), PayoutToolInfoRussianBankAccountModel.class);
            case INTERNATIONAL_BANK_ACCOUNT:
                return conversionService.convert(payoutToolInfo.getInternationalBankAccount(),
                        PayoutToolInfoInternationalBankAccountModel.class);
            case WALLET_INFO:
                return conversionService.convert(payoutToolInfo.getWalletInfo(), PayoutToolInfoWalletInfoModel.class);
            case PAYMENT_INSTITUTION_ACCOUNT:
                return conversionService.convert(payoutToolInfo.getPaymentInstitutionAccount(),
                        PayoutToolInfoPaymentInstitutionAccountModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", payoutToolInfo.getSetField()));
        }
    }
}
