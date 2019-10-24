package com.rbkmoney.cm.model.contract;

import com.rbkmoney.cm.model.ContractParamsModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractCreationModificationModel extends ContractModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "contract_params_id", referencedColumnName = "id")
    private ContractParamsModel contractParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractCreationModificationModel
                && super.canEqual(that);
    }

}
