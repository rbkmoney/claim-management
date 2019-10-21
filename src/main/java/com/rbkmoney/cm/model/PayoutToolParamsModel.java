package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PayoutToolParamsModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String currencySymbolicCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "payout_tool_info_id", referencedColumnName = "id")
    private PayoutToolInfoModel payoutToolInfo;

}
