package com.rbkmoney.cm.model.contract;

import com.rbkmoney.cm.model.ContractAdjustmentParamsModel;
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
public class ContractAdjustmentCreationModificationModel extends ContractAdjustmentModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "contract_adjustment_params_id", referencedColumnName = "id")
    private ContractAdjustmentParamsModel contractAdjustmentParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractAdjustmentCreationModificationModel
                && super.canEqual(that);
    }

}
