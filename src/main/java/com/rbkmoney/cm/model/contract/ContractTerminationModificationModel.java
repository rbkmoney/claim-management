package com.rbkmoney.cm.model.contract;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractTerminationModificationModel extends ContractModificationModel {

    private String reason;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractTerminationModificationModel
                && super.canEqual(that);
    }

}
