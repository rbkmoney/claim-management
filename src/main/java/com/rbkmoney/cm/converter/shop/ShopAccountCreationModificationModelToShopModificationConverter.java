package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopAccountCreationModificationModel;
import com.rbkmoney.damsel.claim_management.ShopAccountParams;
import com.rbkmoney.damsel.claim_management.ShopModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopAccountCreationModificationModelToShopModificationConverter implements ClaimConverter<ShopAccountCreationModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopAccountCreationModificationModel shopAccountCreationModificationModel) {
        return ShopModification.shop_account_creation(
                conversionService.convert(shopAccountCreationModificationModel.getShopAccountParams(), ShopAccountParams.class)
        );
    }
}
