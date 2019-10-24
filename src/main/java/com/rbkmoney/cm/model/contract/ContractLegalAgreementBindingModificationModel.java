package com.rbkmoney.cm.model.contract;

import com.rbkmoney.cm.model.LegalAgreementModel;
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
public class ContractLegalAgreementBindingModificationModel extends ContractModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "legal_agreement_id", referencedColumnName = "id")
    private LegalAgreementModel legalAgreement;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractLegalAgreementBindingModificationModel
                && super.canEqual(that);
    }

}
