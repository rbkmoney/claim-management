package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopDetailsModel;
import com.rbkmoney.cm.model.ShopLocationModel;
import com.rbkmoney.cm.model.ShopParamsModel;
import com.rbkmoney.cm.model.shop.ShopCreationModificationModel;
import com.rbkmoney.damsel.claim_management.ShopParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopParamsToShopCreationModificationModelConverter
        implements ClaimConverter<ShopParams, ShopCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopCreationModificationModel convert(ShopParams shopParams) {
        ShopParamsModel shopParamsModel = new ShopParamsModel();
        shopParamsModel.setCategoryId(shopParams.getCategory().getId());
        shopParamsModel.setContractId(shopParams.getContractId());
        shopParamsModel.setPayoutToolId(shopParams.getPayoutToolId());
        shopParamsModel.setDetails(conversionService.convert(shopParams.getDetails(), ShopDetailsModel.class));
        shopParamsModel.setLocation(conversionService.convert(shopParams.getLocation(), ShopLocationModel.class));
        ShopCreationModificationModel shopCreationModification = new ShopCreationModificationModel();
        shopCreationModification.setShopParams(shopParamsModel);
        return shopCreationModification;
    }
}
