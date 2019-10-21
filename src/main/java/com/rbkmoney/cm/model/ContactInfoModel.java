package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ContactInfoModel {

    @Id
    @GeneratedValue
    private long id;

    private String phoneNumber;

    private String email;

}
