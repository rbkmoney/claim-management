package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.LegalAgreementModel;
import com.rbkmoney.cm.model.PowerOfAttorneyRepresentativeDocumentModel;
import com.rbkmoney.damsel.domain.LegalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class LegalAgreementToPowerOfAttorneyRepresentativeDocumentModelConverter
        implements ClaimConverter<LegalAgreement, PowerOfAttorneyRepresentativeDocumentModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PowerOfAttorneyRepresentativeDocumentModel convert(LegalAgreement legalAgreement) {
        PowerOfAttorneyRepresentativeDocumentModel representativePowerOfAttorneyDocumentModel =
                new PowerOfAttorneyRepresentativeDocumentModel();
        representativePowerOfAttorneyDocumentModel
                .setLegalAgreement(conversionService.convert(legalAgreement, LegalAgreementModel.class));
        return representativePowerOfAttorneyDocumentModel;
    }
}
