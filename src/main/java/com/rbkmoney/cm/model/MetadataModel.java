package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class MetadataModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private byte[] value;

}
