package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PayoutToolInfoWalletInfoModel;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import com.rbkmoney.damsel.domain.WalletInfo;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoWalletInfoModelToPayoutToolInfoConverter implements ClaimConverter<PayoutToolInfoWalletInfoModel, PayoutToolInfo> {

    @Override
    public PayoutToolInfo convert(PayoutToolInfoWalletInfoModel payoutToolInfoWalletInfoModel) {
        return PayoutToolInfo.wallet_info(
                new WalletInfo(payoutToolInfoWalletInfoModel.getWalletId())
        );
    }
}
