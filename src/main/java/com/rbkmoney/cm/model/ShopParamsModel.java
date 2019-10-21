package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ShopParamsModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private int categoryId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "shop_location_id", referencedColumnName = "id")
    private ShopLocationModel location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "shop_details_id", referencedColumnName = "id")
    private ShopDetailsModel details;

    @Column(nullable = false)
    private String contractId;

    @Column(nullable = false)
    private String payoutToolId;

}
