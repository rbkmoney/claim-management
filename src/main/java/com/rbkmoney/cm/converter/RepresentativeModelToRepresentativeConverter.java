package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.RepresentativeModel;
import com.rbkmoney.damsel.domain.Representative;
import com.rbkmoney.damsel.domain.RepresentativeDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RepresentativeModelToRepresentativeConverter implements ClaimConverter<RepresentativeModel, Representative> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Representative convert(RepresentativeModel representativeModel) {
        return new Representative()
                .setFullName(representativeModel.getFullName())
                .setPosition(representativeModel.getPosition())
                .setDocument(conversionService.convert(representativeModel.getDocument(), RepresentativeDocument.class));
    }
}
