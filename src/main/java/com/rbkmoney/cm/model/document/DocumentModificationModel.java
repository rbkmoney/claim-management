package com.rbkmoney.cm.model.document;

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
public class DocumentModificationModel extends ClaimModificationModel {

    @NotNull
    @Column(nullable = false)
    private String documentId;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentModificationTypeEnum documentModificationType;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof DocumentModificationModel
                && documentId.equals(((DocumentModificationModel) that).getDocumentId());
    }

}
