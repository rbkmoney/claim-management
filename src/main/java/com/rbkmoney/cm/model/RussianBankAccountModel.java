package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class RussianBankAccountModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private String account;

    @NotNull
    @Column(nullable = false)
    private String bankName;

    @NotNull
    @Column(nullable = false)
    private String bankPostAccount;

    @NotNull
    @Column(nullable = false)
    private String bankBik;

}
