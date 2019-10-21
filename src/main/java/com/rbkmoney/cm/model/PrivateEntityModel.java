package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PrivateEntityModel {

    @Id
    @GeneratedValue
    private long id;

}
