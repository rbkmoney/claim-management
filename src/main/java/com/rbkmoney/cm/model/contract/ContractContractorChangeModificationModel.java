package com.rbkmoney.cm.model.contract;

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
public class ContractContractorChangeModificationModel extends ContractModificationModel {

    @NotNull
    @Column(nullable = false)
    private String contractorId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractContractorChangeModificationModel
                && super.canEqual(that);
    }

}
