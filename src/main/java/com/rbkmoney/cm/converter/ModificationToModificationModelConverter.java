package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.cm.model.ModificationModel;
import com.rbkmoney.cm.model.PartyModificationModel;
import com.rbkmoney.damsel.claim_management.ClaimModification;
import com.rbkmoney.damsel.claim_management.Modification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ModificationToModificationModelConverter implements ClaimConverter<Modification, ModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ModificationModel convert(Modification modification) {
        switch (modification.getSetField()) {
            case CLAIM_MODFICATION:
                return conversionService.convert(modification.getClaimModfication(), ClaimModificationModel.class);
            case PARTY_MODIFICATION:
                return conversionService.convert(modification.getPartyModification(), PartyModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", modification.getSetField()));
        }
    }
}
