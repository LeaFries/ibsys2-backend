package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Forecast {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int product;

    private int amountCurrentPeriod;

    public Forecast(int product, int amountCurrentPeriod) {
        this.product = product;
        this.amountCurrentPeriod = amountCurrentPeriod;
        this.amountNextPeriod = 0;
        this.amountPeriodThirdPeriod = 0;
        this.amountPeriodFourthPeriod = 0;
    }

    private int amountNextPeriod = 0;

    private int amountPeriodThirdPeriod = 0;

    private int amountPeriodFourthPeriod = 0;

    public Forecast() {

    }
}
