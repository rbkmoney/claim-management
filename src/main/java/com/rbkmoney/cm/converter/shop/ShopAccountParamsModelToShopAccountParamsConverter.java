package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopAccountParamsModel;
import com.rbkmoney.damsel.claim_management.ShopAccountParams;
import com.rbkmoney.damsel.domain.CurrencyRef;
import org.springframework.stereotype.Component;

@Component
public class ShopAccountParamsModelToShopAccountParamsConverter implements ClaimConverter<ShopAccountParamsModel, ShopAccountParams> {
    @Override
    public ShopAccountParams convert(ShopAccountParamsModel shopAccountParamsModel) {
        return new ShopAccountParams()
                .setCurrency(new CurrencyRef(shopAccountParamsModel.getCurrencySymbolicCode()));
    }
}
