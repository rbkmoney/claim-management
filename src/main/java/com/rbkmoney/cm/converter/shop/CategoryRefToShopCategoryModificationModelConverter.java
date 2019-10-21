package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopCategoryModificationModel;
import com.rbkmoney.damsel.domain.CategoryRef;
import org.springframework.stereotype.Component;

@Component
public class CategoryRefToShopCategoryModificationModelConverter implements ClaimConverter<CategoryRef, ShopCategoryModificationModel> {
    @Override
    public ShopCategoryModificationModel convert(CategoryRef categoryRef) {
        ShopCategoryModificationModel shopCategoryModificationModel = new ShopCategoryModificationModel();
        shopCategoryModificationModel.setShopCategoryId(categoryRef.getId());
        return shopCategoryModificationModel;
    }
}
