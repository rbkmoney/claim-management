package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PayoutToolInfoWalletInfoModel;
import com.rbkmoney.damsel.domain.WalletInfo;
import org.springframework.stereotype.Component;

@Component
public class WalletInfoToPayoutToolInfoWalletInfoModelConverter
        implements ClaimConverter<WalletInfo, PayoutToolInfoWalletInfoModel> {
    @Override
    public PayoutToolInfoWalletInfoModel convert(WalletInfo walletInfo) {
        PayoutToolInfoWalletInfoModel contractPayoutToolInfoWalletInfoModel = new PayoutToolInfoWalletInfoModel();
        contractPayoutToolInfoWalletInfoModel.setWalletId(walletInfo.getWalletId());
        return contractPayoutToolInfoWalletInfoModel;
    }
}
