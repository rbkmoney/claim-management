package com.rbkmoney.cm.model.contract;

import com.rbkmoney.cm.model.ServiceAcceptanceActPreferencesModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractReportPreferencesModificationModel extends ContractModificationModel {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_acceptance_act_preferences_id", referencedColumnName = "id")
    private ServiceAcceptanceActPreferencesModel serviceAcceptanceActPreferences;

}
