package com.rbkmoney.cm.converter.document;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.cm.model.document.DocumentModificationModel;
import com.rbkmoney.cm.model.document.DocumentModificationTypeEnum;
import com.rbkmoney.damsel.claim_management.DocumentModificationUnit;
import com.rbkmoney.geck.common.util.TBaseUtil;
import org.springframework.stereotype.Component;

@Component
public class DocumentModificationUnitToDocumentModificationModelConverter implements ClaimConverter<DocumentModificationUnit, DocumentModificationModel> {

    @Override
    public DocumentModificationModel convert(DocumentModificationUnit documentModificationUnit) {
        DocumentModificationModel documentModificationModel = new DocumentModificationModel();
        documentModificationModel.setDocumentId(documentModificationUnit.getId());
        documentModificationModel.setDocumentModificationType(
                TBaseUtil.unionFieldToEnum(
                        documentModificationUnit.getModification(),
                        DocumentModificationTypeEnum.class
                )
        );
        return documentModificationModel;
    }
}
