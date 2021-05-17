package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.InternationalLegalEntityModel;
import com.rbkmoney.damsel.domain.InternationalLegalEntity;
import com.rbkmoney.damsel.domain.LegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class InternationalLegalEntityModelToLegalEntityConverter
        implements ClaimConverter<InternationalLegalEntityModel, LegalEntity> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public LegalEntity convert(InternationalLegalEntityModel internationalLegalEntityModel) {
        return LegalEntity.international_legal_entity(
                conversionService.convert(internationalLegalEntityModel, InternationalLegalEntity.class)
        );
    }
}
