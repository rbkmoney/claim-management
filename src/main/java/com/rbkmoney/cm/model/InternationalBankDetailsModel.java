package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class InternationalBankDetailsModel {

    @Id
    @GeneratedValue
    private long id;

    private String bic;

    private Integer countryCode;

    private String name;

    private String address;

    private String abaRtn;

}
