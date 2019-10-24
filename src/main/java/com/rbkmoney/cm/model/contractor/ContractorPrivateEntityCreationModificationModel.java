package com.rbkmoney.cm.model.contractor;

import com.rbkmoney.cm.model.PrivateEntityModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractorPrivateEntityCreationModificationModel extends ContractorCreationModificationModel {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "private_entity_id", referencedColumnName = "id")
    PrivateEntityModel privateEntity;

    @Override
    public boolean canEqual(final Object that) {
        return super.canEqual(that);
    }

}
