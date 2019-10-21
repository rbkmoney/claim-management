package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.RussianLegalEntityModel;
import com.rbkmoney.damsel.domain.LegalEntity;
import com.rbkmoney.damsel.domain.RussianLegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianLegalEntityModelToLegalEntityConverter implements ClaimConverter<RussianLegalEntityModel, LegalEntity> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public LegalEntity convert(RussianLegalEntityModel russianLegalEntityModel) {
        return LegalEntity.russian_legal_entity(
                conversionService.convert(russianLegalEntityModel, RussianLegalEntity.class)
        );
    }
}
