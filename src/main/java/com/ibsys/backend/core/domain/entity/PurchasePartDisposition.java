package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PurchasePartDisposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_number")
    private Integer itemNumber;

    @Column(name = "delivery_time")
    private Double deliveryTime;

    @Column(name = "initial_stock")
    private Integer initialStock;

    @Column(name = "requirement_n")
    private Integer requirementN;

    @Column(name = "requirement_n_plus_one")
    private Integer requirementNplusOne;

    @Column(name = "requirement_n_plus_two")
    private Integer requirementNplusTwo;

    @Column(name = "requirement_n_plus_three")
    private Integer requirementNplusThree;
}
