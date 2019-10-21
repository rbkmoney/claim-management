package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ServiceAcceptanceActPreferencesModel {

    @Id
    @GeneratedValue
    private long id;

    private int schedulerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "signer_id", referencedColumnName = "id")
    private RepresentativeModel signer;

}
