package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopLocationModel;
import com.rbkmoney.cm.model.shop.ShopLocationModificationModel;
import com.rbkmoney.damsel.domain.ShopLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopLocationToShopLocationModificationModelConverter
        implements ClaimConverter<ShopLocation, ShopLocationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopLocationModificationModel convert(ShopLocation shopLocation) {
        ShopLocationModificationModel shopLocationModificationModel = new ShopLocationModificationModel();
        shopLocationModificationModel.setLocation(conversionService.convert(shopLocation, ShopLocationModel.class));
        return shopLocationModificationModel;
    }
}
