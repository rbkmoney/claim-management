package com.rbkmoney.cm.model.comment;

import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.cm.model.contract.ContractModificationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentModificationModel extends ClaimModificationModel {

    @Column(nullable = false)
    private String commentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentModificationTypeEnum commentModificationType;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof CommentModificationModel
                && commentId.equals(((CommentModificationModel) that).getCommentId());
    }
}
