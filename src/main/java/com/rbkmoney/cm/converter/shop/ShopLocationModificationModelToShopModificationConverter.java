package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopLocationModificationModel;
import com.rbkmoney.damsel.claim_management.ShopModification;
import com.rbkmoney.damsel.domain.ShopLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopLocationModificationModelToShopModificationConverter
        implements ClaimConverter<ShopLocationModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopLocationModificationModel shopLocationModificationModel) {
        return ShopModification.location_modification(
                conversionService.convert(shopLocationModificationModel.getLocation(), ShopLocation.class)
        );
    }
}
