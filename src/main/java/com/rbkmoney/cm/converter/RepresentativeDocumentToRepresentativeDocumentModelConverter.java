package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ArticlesOfAssociationRepresentativeDocumentModel;
import com.rbkmoney.cm.model.PowerOfAttorneyRepresentativeDocumentModel;
import com.rbkmoney.cm.model.RepresentativeDocumentModel;
import com.rbkmoney.damsel.domain.RepresentativeDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RepresentativeDocumentToRepresentativeDocumentModelConverter implements ClaimConverter<RepresentativeDocument, RepresentativeDocumentModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RepresentativeDocumentModel convert(RepresentativeDocument representativeDocument) {
        switch (representativeDocument.getSetField()) {
            case POWER_OF_ATTORNEY:
                return conversionService.convert(representativeDocument.getPowerOfAttorney(), PowerOfAttorneyRepresentativeDocumentModel.class);
            case ARTICLES_OF_ASSOCIATION:
                return new ArticlesOfAssociationRepresentativeDocumentModel();
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", representativeDocument.getSetField()));
        }
    }
}
