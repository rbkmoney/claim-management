package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class ShopParamsModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private int categoryId;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "shop_location_id", referencedColumnName = "id")
    private ShopLocationModel location;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "shop_details_id", referencedColumnName = "id")
    private ShopDetailsModel details;

    @NotNull
    @Column(nullable = false)
    private String contractId;

    @NotNull
    @Column(nullable = false)
    private String payoutToolId;

}
