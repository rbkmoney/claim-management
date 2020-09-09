package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.cm.model.ModificationModel;
import com.rbkmoney.cm.model.PartyModificationModel;
import com.rbkmoney.damsel.claim_management.ModificationChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ModificationChangeToModificationModelConverter implements ClaimConverter<ModificationChange, ModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ModificationModel convert(ModificationChange modificationChange) {
        switch (modificationChange.getSetField()) {
            case CLAIM_MODIFICATION:
                return conversionService.convert(modificationChange.getClaimModification(), ClaimModificationModel.class);
            case PARTY_MODIFICATION:
                return conversionService.convert(modificationChange.getPartyModification(), PartyModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", modificationChange.getSetField()));
        }
    }
}
