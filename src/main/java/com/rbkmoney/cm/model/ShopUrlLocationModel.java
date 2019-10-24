package com.rbkmoney.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@DiscriminatorValue("url")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ShopUrlLocationModel extends ShopLocationModel {

    @NotNull
    private String url;

}
