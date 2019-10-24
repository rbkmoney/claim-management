package com.rbkmoney.cm.model.contract;

import com.rbkmoney.cm.model.PartyModificationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class ContractModificationModel extends PartyModificationModel {

    @Column(nullable = false)
    private String contractId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractModificationModel
                && contractId.equals(((ContractModificationModel) that).getContractId());
    }

}
