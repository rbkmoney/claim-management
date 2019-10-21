package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopAccountParamsModel;
import com.rbkmoney.cm.model.shop.ShopAccountCreationModificationModel;
import com.rbkmoney.damsel.claim_management.ShopAccountParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopAccountParamsToShopAccountCreationModificationModelConverter implements ClaimConverter<ShopAccountParams, ShopAccountCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopAccountCreationModificationModel convert(ShopAccountParams shopAccountParams) {
        ShopAccountCreationModificationModel shopAccountCreationModificationModel = new ShopAccountCreationModificationModel();
        shopAccountCreationModificationModel.setShopAccountParams(conversionService.convert(shopAccountParams, ShopAccountParamsModel.class));
        return shopAccountCreationModificationModel;
    }
}
