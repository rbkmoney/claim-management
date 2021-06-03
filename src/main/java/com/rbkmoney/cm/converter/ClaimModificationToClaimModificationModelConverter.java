package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.cm.model.comment.CommentModificationModel;
import com.rbkmoney.cm.model.document.DocumentModificationModel;
import com.rbkmoney.cm.model.external.info.ExternalInfoModificationModel;
import com.rbkmoney.cm.model.file.FileModificationModel;
import com.rbkmoney.cm.model.status.StatusModificationModel;
import com.rbkmoney.damsel.claim_management.ClaimModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ClaimModificationToClaimModificationModelConverter
        implements ClaimConverter<ClaimModification, ClaimModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModificationModel convert(ClaimModification claimModification) {
        switch (claimModification.getSetField()) {
            case FILE_MODIFICATION:
                return conversionService.convert(claimModification.getFileModification(), FileModificationModel.class);
            case STATUS_MODIFICATION:
                return conversionService
                        .convert(claimModification.getStatusModification(), StatusModificationModel.class);
            case COMMENT_MODIFICATION:
                return conversionService
                        .convert(claimModification.getCommentModification(), CommentModificationModel.class);
            case DOCUMENT_MODIFICATION:
                return conversionService
                        .convert(claimModification.getDocumentModification(), DocumentModificationModel.class);
            case EXTERNAL_INFO_MODIFICATION:
                return conversionService
                        .convert(claimModification.getExternalInfoModification(), ExternalInfoModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", claimModification.getSetField()));
        }
    }
}
