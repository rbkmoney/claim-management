package com.rbkmoney.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@DiscriminatorValue("payment_institution_account")
@EqualsAndHashCode(callSuper = true)
public class PayoutToolInfoPaymentInstitutionAccountModel extends PayoutToolInfoModel {
}
