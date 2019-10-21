package com.rbkmoney.cm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class ClaimModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String partyId;

    @Version
    @Column(nullable = false)
    private int revision;

    @Embedded
    private ClaimStatusModel claimStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OrderBy
    @JoinColumn(name = "claim_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ModificationModel> modifications;

    @JoinColumn(name = "claim_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MetadataModel> metadata;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimModel that = (ClaimModel) o;
        return id == that.id &&
                revision == that.revision &&
                Objects.equals(partyId, that.partyId) &&
                Objects.equals(claimStatus, that.claimStatus) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(List.copyOf(modifications), List.copyOf(that.modifications)) &&
                Objects.equals(List.copyOf(metadata), List.copyOf(that.metadata));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, partyId, revision, claimStatus, createdAt, updatedAt, List.copyOf(modifications), List.copyOf(metadata));
    }
}
