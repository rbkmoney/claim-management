package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.InternationalLegalEntityModel;
import com.rbkmoney.cm.model.LegalEntityModel;
import com.rbkmoney.cm.model.RussianLegalEntityModel;
import com.rbkmoney.damsel.domain.LegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class LegalEntityToLegalEntityModelConverter implements ClaimConverter<LegalEntity, LegalEntityModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public LegalEntityModel convert(LegalEntity legalEntity) {
        switch (legalEntity.getSetField()) {
            case RUSSIAN_LEGAL_ENTITY:
                return conversionService.convert(legalEntity.getRussianLegalEntity(), RussianLegalEntityModel.class);
            case INTERNATIONAL_LEGAL_ENTITY:
                return conversionService.convert(legalEntity.getInternationalLegalEntity(), InternationalLegalEntityModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", legalEntity.getSetField()));
        }
    }
}
