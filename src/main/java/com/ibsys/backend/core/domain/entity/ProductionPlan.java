package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
public class ProductionPlan {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Positive
    private int article;

    @Positive
    @Column(name = "period_n")
    private int periodN;

    @Positive
    @Column(name = "period_n_plus_one")
    private int periodNplusOne;

    @Positive
    @Column(name = "period_n_plus_two")
    private int periodNplusTwo;

    @Positive
    @Column(name = "period_n_plus_three")
    private int periodNplusThree;
}
