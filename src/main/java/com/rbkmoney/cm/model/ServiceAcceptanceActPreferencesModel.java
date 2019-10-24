package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class ServiceAcceptanceActPreferencesModel {

    @Id
    @GeneratedValue
    private long id;

    private int schedulerId;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "signer_id", referencedColumnName = "id")
    private RepresentativeModel signer;

}
