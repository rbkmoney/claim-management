package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.CashRegisterParamsModel;
import com.rbkmoney.cm.model.shop.ShopCashRegisterCreationModificationModel;
import com.rbkmoney.damsel.claim_management.CashRegisterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class CashRegisterParamsToCreationConverter
        implements ClaimConverter<CashRegisterParams, ShopCashRegisterCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopCashRegisterCreationModificationModel convert(CashRegisterParams cashRegisterParams) {
        ShopCashRegisterCreationModificationModel shopCashRegisterCreationModificationModel =
                new ShopCashRegisterCreationModificationModel();
        CashRegisterParamsModel cashRegisterParamsModel =
                conversionService.convert(cashRegisterParams, CashRegisterParamsModel.class);
        shopCashRegisterCreationModificationModel.setCashRegisterParamsModel(cashRegisterParamsModel);

        return shopCashRegisterCreationModificationModel;
    }

}
