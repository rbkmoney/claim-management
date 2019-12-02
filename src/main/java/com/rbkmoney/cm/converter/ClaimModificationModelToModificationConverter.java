package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.damsel.claim_management.ClaimModification;
import com.rbkmoney.damsel.claim_management.Modification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ClaimModificationModelToModificationConverter implements ClaimConverter<ClaimModificationModel, Modification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Modification convert(ClaimModificationModel claimModificationModel) {
        return Modification.claim_modification(
                conversionService.convert(
                        claimModificationModel,
                        ClaimModification.class
                )
        );
    }
}
