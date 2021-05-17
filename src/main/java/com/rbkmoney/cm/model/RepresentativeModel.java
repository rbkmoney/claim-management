package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class RepresentativeModel {

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
    @Id
    @GeneratedValue
    private long id;

}
