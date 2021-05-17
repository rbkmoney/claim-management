package com.rbkmoney.cm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

@Data
@Entity
public class ClaimModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private String partyId;

    @Version
    @Column(nullable = false)
    private int revision;

    @Embedded
    private ClaimStatusModel claimStatus;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OrderBy
    @JoinColumn(name = "claim_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @SQLDelete(sql = "UPDATE cm.modification_model SET deleted = true WHERE id = ?")
    @Where(clause = "deleted <> true")
    private List<ModificationModel> modifications;

    @JoinColumn(name = "claim_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MetadataModel> metadata;

}
