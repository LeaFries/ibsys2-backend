package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "future_inward_stockmovement")
@Data
public class FutureInwardStockmovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_period")
    private Integer orderperiod;

    /**
     3 = JIT
     4 = fast
     5 = normal
     */
    private Integer mode;

    private Integer article;

    private Integer amount;
}
