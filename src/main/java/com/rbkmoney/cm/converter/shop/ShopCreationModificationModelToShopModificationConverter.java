package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopCreationModificationModel;
import com.rbkmoney.damsel.claim_management.ShopModification;
import com.rbkmoney.damsel.claim_management.ShopParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCreationModificationModelToShopModificationConverter
        implements ClaimConverter<ShopCreationModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopCreationModificationModel shopCreationModificationModel) {
        return ShopModification.creation(
                conversionService.convert(shopCreationModificationModel.getShopParams(), ShopParams.class)
        );
    }
}
