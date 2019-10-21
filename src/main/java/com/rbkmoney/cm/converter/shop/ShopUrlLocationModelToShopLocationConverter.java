package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopUrlLocationModel;
import com.rbkmoney.damsel.domain.ShopLocation;
import org.springframework.stereotype.Component;

@Component
public class ShopUrlLocationModelToShopLocationConverter implements ClaimConverter<ShopUrlLocationModel, ShopLocation> {
    @Override
    public ShopLocation convert(ShopUrlLocationModel shopUrlLocationModel) {
        return ShopLocation.url(shopUrlLocationModel.getUrl());
    }
}
