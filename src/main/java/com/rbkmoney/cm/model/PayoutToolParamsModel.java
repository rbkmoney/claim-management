package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class PayoutToolParamsModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private String currencySymbolicCode;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "payout_tool_info_id", referencedColumnName = "id")
    private PayoutToolInfoModel payoutToolInfo;

}
