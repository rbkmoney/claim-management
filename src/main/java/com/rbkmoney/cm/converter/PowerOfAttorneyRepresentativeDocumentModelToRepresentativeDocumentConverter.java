package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.PowerOfAttorneyRepresentativeDocumentModel;
import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.damsel.domain.RepresentativeDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PowerOfAttorneyRepresentativeDocumentModelToRepresentativeDocumentConverter
        implements ClaimConverter<PowerOfAttorneyRepresentativeDocumentModel, RepresentativeDocument> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RepresentativeDocument convert(
            PowerOfAttorneyRepresentativeDocumentModel powerOfAttorneyRepresentativeDocumentModel) {
        return RepresentativeDocument.power_of_attorney(
                conversionService
                        .convert(powerOfAttorneyRepresentativeDocumentModel.getLegalAgreement(), LegalAgreement.class)
        );
    }
}
