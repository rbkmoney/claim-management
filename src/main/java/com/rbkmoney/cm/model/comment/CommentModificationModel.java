package com.rbkmoney.cm.model.comment;

import com.rbkmoney.cm.model.ClaimModificationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentModificationModel extends ClaimModificationModel {

    @NotNull
    @Column(nullable = false)
    private String commentId;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentModificationTypeEnum commentModificationType;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof CommentModificationModel
                && commentId.equals(((CommentModificationModel) that).getCommentId())
                && commentModificationType == ((CommentModificationModel) that).getCommentModificationType();
    }
}
