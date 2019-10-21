package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ContractAdjustmentParamsModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private int contractTemplateId;

}
