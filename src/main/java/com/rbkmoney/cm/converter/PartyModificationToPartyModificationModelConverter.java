package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PartyModificationModel;
import com.rbkmoney.cm.model.contract.ContractModificationModel;
import com.rbkmoney.cm.model.contractor.ContractorModificationModel;
import com.rbkmoney.cm.model.shop.ShopModificationModel;
import com.rbkmoney.damsel.claim_management.PartyModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PartyModificationToPartyModificationModelConverter
        implements ClaimConverter<PartyModification, PartyModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModificationModel convert(PartyModification partyModification) {
        switch (partyModification.getSetField()) {
            case SHOP_MODIFICATION:
                return conversionService.convert(partyModification.getShopModification(), ShopModificationModel.class);
            case CONTRACT_MODIFICATION:
                return conversionService
                        .convert(partyModification.getContractModification(), ContractModificationModel.class);
            case CONTRACTOR_MODIFICATION:
                return conversionService
                        .convert(partyModification.getContractorModification(), ContractorModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", partyModification.getSetField()));
        }
    }
}
