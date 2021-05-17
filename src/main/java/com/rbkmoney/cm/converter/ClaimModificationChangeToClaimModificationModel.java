package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.cm.model.comment.CommentModificationModel;
import com.rbkmoney.cm.model.document.DocumentModificationModel;
import com.rbkmoney.cm.model.file.FileModificationModel;
import com.rbkmoney.damsel.claim_management.ClaimModificationChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ClaimModificationChangeToClaimModificationModel
        implements ClaimConverter<ClaimModificationChange, ClaimModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModificationModel convert(ClaimModificationChange claimModificationChange) {
        switch (claimModificationChange.getSetField()) {
            case FILE_MODIFICATION:
                return conversionService
                        .convert(claimModificationChange.getFileModification(), FileModificationModel.class);
            case COMMENT_MODIFICATION:
                return conversionService
                        .convert(claimModificationChange.getCommentModification(), CommentModificationModel.class);
            case DOCUMENT_MODIFICATION:
                return conversionService
                        .convert(claimModificationChange.getDocumentModification(), DocumentModificationModel.class);
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", claimModificationChange.getSetField()));
        }
    }
}
