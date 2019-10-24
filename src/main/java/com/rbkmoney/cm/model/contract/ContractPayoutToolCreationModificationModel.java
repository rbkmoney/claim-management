package com.rbkmoney.cm.model.contract;

import com.rbkmoney.cm.model.PayoutToolParamsModel;
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
public class ContractPayoutToolCreationModificationModel extends ContractPayoutToolModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "payout_tool_params_id", referencedColumnName = "id")
    private PayoutToolParamsModel payoutToolParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractPayoutToolCreationModificationModel
                && super.canEqual(that);
    }

}
