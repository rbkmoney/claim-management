package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class RepresentativeModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    public String position;

    @NotNull
    @Column(nullable = false)
    public String fullName;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "document_id", referencedColumnName = "id")
    public RepresentativeDocumentModel document;

}
