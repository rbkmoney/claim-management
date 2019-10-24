package com.rbkmoney.cm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OrderBy
    @JoinColumn(name = "claim_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ModificationModel> modifications;

    @JoinColumn(name = "claim_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MetadataModel> metadata;

}
