package com.rbkmoney.cm.model.contract;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractContractorChangeModificationModel extends ContractModificationModel {

    @Column(nullable = false)
    private String contractorId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractContractorChangeModificationModel
                && super.canEqual(that);
    }

}
