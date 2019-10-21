package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class RepresentativeModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    public String position;

    @Column(nullable = false)
    public String fullName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "document_id", referencedColumnName = "id")
    public RepresentativeDocumentModel document;

}
