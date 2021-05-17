package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopAccountParamsModel;
import com.rbkmoney.damsel.claim_management.ShopAccountParams;
import org.springframework.stereotype.Component;

@Component
public class ShopAccountParamsToShopAccountParamsModelConverter
        implements ClaimConverter<ShopAccountParams, ShopAccountParamsModel> {
    @Override
    public ShopAccountParamsModel convert(ShopAccountParams shopAccountParams) {
        ShopAccountParamsModel shopAccountParamsModel = new ShopAccountParamsModel();
        shopAccountParamsModel.setCurrencySymbolicCode(shopAccountParams.getCurrency().getSymbolicCode());
        return shopAccountParamsModel;
    }
}
