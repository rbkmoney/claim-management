package com.rbkmoney.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("russian_private_entity")
public class RussianPrivateEntityModel extends PrivateEntityModel {

    //    @Column(nullable = false)
    private String firstName;

    //    @Column(nullable = false)
    private String secondName;

    //    @Column(nullable = false)
    private String middleName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(/*nullable = false, */name = "contact_info_id", referencedColumnName = "id")
    private ContactInfoModel contactInfo;

}
