package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopLocationModel;
import com.rbkmoney.cm.model.ShopUrlLocationModel;
import com.rbkmoney.damsel.domain.ShopLocation;
import org.springframework.stereotype.Component;

@Component
public class ShopLocationToShopLocationModelConverter implements ClaimConverter<ShopLocation, ShopLocationModel> {

    @Override
    public ShopLocationModel convert(ShopLocation shopLocation) {
        switch (shopLocation.getSetField()) {
            case URL:
                ShopUrlLocationModel shopUrlLocationModel = new ShopUrlLocationModel();
                shopUrlLocationModel.setUrl(shopLocation.getUrl());
                return shopUrlLocationModel;
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", shopLocation.getSetField()));
        }
    }
}
