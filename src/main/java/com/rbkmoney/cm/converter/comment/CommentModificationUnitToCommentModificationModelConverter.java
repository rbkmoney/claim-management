package com.rbkmoney.cm.converter.comment;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.cm.model.comment.CommentModificationModel;
import com.rbkmoney.cm.model.comment.CommentModificationTypeEnum;
import com.rbkmoney.damsel.claim_management.CommentModificationUnit;
import com.rbkmoney.geck.common.util.TBaseUtil;
import org.springframework.stereotype.Component;

@Component
public class CommentModificationUnitToCommentModificationModelConverter implements ClaimConverter<CommentModificationUnit, CommentModificationModel> {

    @Override
    public CommentModificationModel convert(CommentModificationUnit commentModificationUnit) {
        CommentModificationModel commentModificationModel = new CommentModificationModel();
        commentModificationModel.setCommentId(commentModificationUnit.getId());
        commentModificationModel.setCommentModificationType(
                TBaseUtil.unionFieldToEnum(
                        commentModificationUnit.getModification(),
                        CommentModificationTypeEnum.class
                )
        );
        return commentModificationModel;
    }
}
