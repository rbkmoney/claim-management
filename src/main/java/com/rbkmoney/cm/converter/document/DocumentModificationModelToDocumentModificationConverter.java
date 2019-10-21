package com.rbkmoney.cm.converter.document;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.document.DocumentModificationModel;
import com.rbkmoney.damsel.claim_management.DocumentCreated;
import com.rbkmoney.damsel.claim_management.DocumentModification;
import org.springframework.stereotype.Component;

@Component
public class DocumentModificationModelToDocumentModificationConverter implements ClaimConverter<DocumentModificationModel, DocumentModification> {
    @Override
    public DocumentModification convert(DocumentModificationModel documentModificationModel) {
        switch (documentModificationModel.getDocumentModificationType()) {
            case creation:
                return DocumentModification.creation(new DocumentCreated());
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", documentModificationModel.getDocumentModificationType()));
        }
    }
}
