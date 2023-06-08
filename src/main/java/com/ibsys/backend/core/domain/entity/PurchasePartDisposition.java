package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PurchasePartDisposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer itemNumber;

    private Integer initialStock;

    private Integer requirementN;

    private Integer requirementNplusOne;

    private Integer requirementNplusTwo;

    private Integer requirementNplusThree;
}
