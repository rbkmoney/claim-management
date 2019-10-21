package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopParamsModel;
import com.rbkmoney.damsel.claim_management.ShopParams;
import com.rbkmoney.damsel.domain.CategoryRef;
import com.rbkmoney.damsel.domain.ShopDetails;
import com.rbkmoney.damsel.domain.ShopLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopParamsModelToShopParamsConverter implements ClaimConverter<ShopParamsModel, ShopParams> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopParams convert(ShopParamsModel shopParamsModel) {
        return new ShopParams()
                .setContractId(shopParamsModel.getContractId())
                .setPayoutToolId(shopParamsModel.getPayoutToolId())
                .setCategory(new CategoryRef(shopParamsModel.getCategoryId()))
                .setDetails(conversionService.convert(shopParamsModel.getDetails(), ShopDetails.class))
                .setLocation(conversionService.convert(shopParamsModel.getLocation(), ShopLocation.class));
    }
}
