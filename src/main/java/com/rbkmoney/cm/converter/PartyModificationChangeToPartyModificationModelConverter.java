package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PartyModificationModel;
import com.rbkmoney.cm.model.contract.ContractModificationModel;
import com.rbkmoney.cm.model.contractor.ContractorModificationModel;
import com.rbkmoney.cm.model.shop.ShopModificationModel;
import com.rbkmoney.damsel.claim_management.PartyModificationChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PartyModificationChangeToPartyModificationModelConverter implements ClaimConverter<PartyModificationChange, PartyModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModificationModel convert(PartyModificationChange partyModificationChange) {
        switch (partyModificationChange.getSetField()) {
            case SHOP_MODIFICATION:
                return conversionService.convert(partyModificationChange.getShopModification(), ShopModificationModel.class);
            case CONTRACT_MODIFICATION:
                return conversionService.convert(partyModificationChange.getContractModification(), ContractModificationModel.class);
            case CONTRACTOR_MODIFICATION:
                return conversionService.convert(partyModificationChange.getContractorModification(), ContractorModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", partyModificationChange.getSetField()));
        }
    }
}
