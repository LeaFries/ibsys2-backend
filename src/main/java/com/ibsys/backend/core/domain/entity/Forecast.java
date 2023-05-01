package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Forecast {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int product;

    private int periodN;

    public Forecast(int product, int periodN) {
        this.product = product;
        this.periodN = periodN;
        this.periodNplusOne = 0;
        this.periodNplusTwo = 0;
        this.periodNplusThree = 0;
    }

    private int periodNplusOne;

    private int periodNplusTwo;

    private int periodNplusThree;

    public Forecast() {
        
    }
}
