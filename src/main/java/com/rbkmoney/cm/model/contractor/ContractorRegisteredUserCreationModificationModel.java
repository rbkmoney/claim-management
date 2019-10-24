package com.rbkmoney.cm.model.contractor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractorRegisteredUserCreationModificationModel extends ContractorCreationModificationModel {

    @Column(nullable = false)
    private String email;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractorRegisteredUserCreationModificationModel
                && super.canEqual(that);
    }

}
