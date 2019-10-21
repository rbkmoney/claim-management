package com.rbkmoney.cm.converter.document;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.document.DocumentModificationModel;
import com.rbkmoney.damsel.claim_management.ClaimModification;
import com.rbkmoney.damsel.claim_management.DocumentModification;
import com.rbkmoney.damsel.claim_management.DocumentModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class DocumentModificationModelToClaimModificationConverter implements ClaimConverter<DocumentModificationModel, ClaimModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModification convert(DocumentModificationModel documentModificationModel) {
        return ClaimModification.document_modification(
                new DocumentModificationUnit()
                        .setId(documentModificationModel.getDocumentId())
                        .setModification(
                                conversionService.convert(documentModificationModel, DocumentModification.class)
                        )
        );
    }
}
