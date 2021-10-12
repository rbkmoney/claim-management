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
public class ShopModificationUnitToShopModificationModelConverter
        implements ClaimConverter<ShopModificationUnit, ShopModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModificationModel convert(ShopModificationUnit shopModificationUnit) {
        ShopModification shopModification = shopModificationUnit.getModification();
        ShopModificationModel shopModificationModel =
                conversionService.convert(shopModification, ShopModificationModel.class);
        if (shopModificationModel == null) {
            throw new IllegalStateException("ShopModificationModel can't be null");
        }
        shopModificationModel.setShopId(shopModificationUnit.getId());
        return shopModificationModel;
    }
}
