package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class RussianBankAccountModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String bankPostAccount;

    @Column(nullable = false)
    private String bankBik;

}
