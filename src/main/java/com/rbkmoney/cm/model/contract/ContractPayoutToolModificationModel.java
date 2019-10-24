package com.rbkmoney.cm.model.contract;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class ContractPayoutToolModificationModel extends ContractModificationModel {

    @NotNull
    @Column(nullable = false)
    private String payoutToolId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractPayoutToolModificationModel
                && payoutToolId.equals(((ContractPayoutToolModificationModel) that).getPayoutToolId());
    }

}
