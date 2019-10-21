package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopDetailsModel;
import com.rbkmoney.damsel.domain.ShopDetails;
import org.springframework.stereotype.Component;

@Component
public class ShopDetailsModelToShopDetailsConverter implements ClaimConverter<ShopDetailsModel, ShopDetails> {
    @Override
    public ShopDetails convert(ShopDetailsModel shopDetailsModel) {
        return new ShopDetails()
                .setName(shopDetailsModel.getName())
                .setDescription(shopDetailsModel.getDescription());
    }
}
