package com.rbkmoney.cm.model.external.info;

import com.rbkmoney.cm.model.ClaimModificationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ExternalInfoModificationModel extends ClaimModificationModel {

    @NotNull
    @Column(nullable = false)
    private String documentId;

    @Column
    private String roistatId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ExternalInfoModificationModel
                && documentId.equals(((ExternalInfoModificationModel) that).getDocumentId());
    }

}
