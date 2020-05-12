package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopCashRegisterModificationModel;
import com.rbkmoney.damsel.claim_management.CashRegisterModification;
import com.rbkmoney.damsel.claim_management.CashRegisterModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCashRegisterModificationModelToShopCashRegisterModificationUnitConverter
        implements ClaimConverter<ShopCashRegisterModificationModel, CashRegisterModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public CashRegisterModificationUnit convert(ShopCashRegisterModificationModel cashRegisterModificationModel) {
        return new CashRegisterModificationUnit()
                .setId(cashRegisterModificationModel.getRegisterId())
                .setModification(
                        conversionService.convert(cashRegisterModificationModel, CashRegisterModification.class)
                );
    }

}
