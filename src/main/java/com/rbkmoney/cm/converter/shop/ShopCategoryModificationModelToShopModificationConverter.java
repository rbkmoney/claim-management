package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopCategoryModificationModel;
import com.rbkmoney.damsel.claim_management.ShopModification;
import com.rbkmoney.damsel.domain.CategoryRef;
import org.springframework.stereotype.Component;

@Component
public class ShopCategoryModificationModelToShopModificationConverter implements ClaimConverter<ShopCategoryModificationModel, ShopModification> {
    @Override
    public ShopModification convert(ShopCategoryModificationModel shopCategoryModificationModel) {
        return ShopModification.category_modification(
                new CategoryRef(shopCategoryModificationModel.getShopCategoryId())
        );
    }
}
