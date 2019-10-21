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

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractAdjustmentCreationModificationModel extends ContractAdjustmentModificationModel {

    @JoinColumn(nullable = false, name = "contract_adjustment_params_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private ContractAdjustmentParamsModel contractAdjustmentParams;

}
