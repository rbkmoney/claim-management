package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ContractParamsModel {

    @Id
    @GeneratedValue
    private long id;

    private String contractorId;

    private Integer contractTemplateId;

    private Integer paymentInstitutionId;

}
