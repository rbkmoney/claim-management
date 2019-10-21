package com.rbkmoney.cm.model.contract;

import com.rbkmoney.cm.model.PayoutToolInfoModel;
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
public class ContractPayoutToolChangeModificationModel extends ContractPayoutToolModificationModel {

    @JoinColumn(nullable = false, name = "payout_tool_info_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private PayoutToolInfoModel payoutToolInfoModel;

}
