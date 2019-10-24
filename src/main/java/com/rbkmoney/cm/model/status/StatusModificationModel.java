package com.rbkmoney.cm.model.status;

import com.rbkmoney.cm.model.ClaimModificationModel;
import com.rbkmoney.cm.model.ClaimStatusModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StatusModificationModel extends ClaimModificationModel {

    @Embedded
    private ClaimStatusModel claimStatus;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusModificationTypeEnum statusModificationType;

    @Override
    public boolean canEqual(Object that) {
        return that instanceof StatusModificationModel;
    }

}
