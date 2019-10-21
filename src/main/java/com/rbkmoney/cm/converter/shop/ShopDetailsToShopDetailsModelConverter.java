package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopDetailsModel;
import com.rbkmoney.damsel.domain.ShopDetails;
import org.springframework.stereotype.Component;

@Component
public class ShopDetailsToShopDetailsModelConverter implements ClaimConverter<ShopDetails, ShopDetailsModel> {
    @Override
    public ShopDetailsModel convert(ShopDetails shopDetails) {
        ShopDetailsModel shopDetailsModel = new ShopDetailsModel();
        shopDetailsModel.setName(shopDetails.getName());
        shopDetailsModel.setDescription(shopDetails.getDescription());
        return shopDetailsModel;
    }
}
