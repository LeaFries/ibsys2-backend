package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class ProductionPlan {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Positive
    private int product;

    private int amountCurrentPeriod;

    private int amountNextPeriod;

    private int amountPeriodThirdPeriod;

    private int amountPeriodFourthPeriod;
}
