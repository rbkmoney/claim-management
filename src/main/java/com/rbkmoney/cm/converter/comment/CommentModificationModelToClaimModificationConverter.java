package com.rbkmoney.cm.converter.comment;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.comment.CommentModificationModel;
import com.rbkmoney.damsel.claim_management.ClaimModification;
import com.rbkmoney.damsel.claim_management.CommentModification;
import com.rbkmoney.damsel.claim_management.CommentModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class CommentModificationModelToClaimModificationConverter
        implements ClaimConverter<CommentModificationModel, ClaimModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModification convert(CommentModificationModel commentModificationModel) {
        return ClaimModification.comment_modification(
                new CommentModificationUnit()
                        .setId(commentModificationModel.getCommentId())
                        .setModification(
                                conversionService.convert(commentModificationModel, CommentModification.class)
                        )
        );
    }
}
