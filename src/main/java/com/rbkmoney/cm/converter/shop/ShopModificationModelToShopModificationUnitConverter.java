package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopModificationModel;
import com.rbkmoney.damsel.claim_management.ShopModification;
import com.rbkmoney.damsel.claim_management.ShopModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopModificationModelToShopModificationUnitConverter implements ClaimConverter<ShopModificationModel, ShopModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModificationUnit convert(ShopModificationModel shopModificationModel) {
        return new ShopModificationUnit()
                .setId(shopModificationModel.getShopId())
                .setModification(
                        conversionService.convert(shopModificationModel, ShopModification.class)
                );
    }
}
