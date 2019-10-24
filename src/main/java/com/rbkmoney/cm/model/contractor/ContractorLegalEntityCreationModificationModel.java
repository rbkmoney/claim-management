package com.rbkmoney.cm.model.contractor;

import com.rbkmoney.cm.model.LegalEntityModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractorLegalEntityCreationModificationModel extends ContractorCreationModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "legal_entity_id", referencedColumnName = "id")
    private LegalEntityModel legalEntity;

    @Override
    public boolean canEqual(final Object that) {
        return super.canEqual(that);
    }

}
