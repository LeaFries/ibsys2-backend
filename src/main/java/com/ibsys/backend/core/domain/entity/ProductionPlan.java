package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionPlan {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Positive
    private int article;

    @Column(name = "period_n")
    private int periodN;

    @Column(name = "period_n_plus_one")
    private int periodNplusOne;

    @Column(name = "period_n_plus_two")
    private int periodNplusTwo;

    @Column(name = "period_n_plus_three")
    private int periodNplusThree;
}
