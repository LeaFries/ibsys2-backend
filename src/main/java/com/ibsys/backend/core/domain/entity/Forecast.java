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

    @Positive
    private int product;

    @Positive
    //@Column(name = "periode_n")
    private int periodN;

    @Positive
    //@Column(name = "period_n_plus_one")
    private int periodNplusOne;

    @Positive
    //@Column(name = "period_n_plus_two")
    private int periodNplusTwo;

    @Positive
    //@Column(name = "period_n_plus_three")
    private int periodNplusThree;
}
