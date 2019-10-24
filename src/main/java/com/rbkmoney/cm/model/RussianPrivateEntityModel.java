package com.rbkmoney.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("russian_private_entity")
public class RussianPrivateEntityModel extends PrivateEntityModel {

    @NotNull
    private String firstName;

    @NotNull
    private String secondName;

    @NotNull
    private String middleName;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_info_id", referencedColumnName = "id")
    private ContactInfoModel contactInfo;

}
