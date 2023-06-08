package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "orders_in_work_workplace")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrdersInWorkWorkplace {
    @Id
    private int id;
    @Column(name = "period")
    private int period;
    @Column(name = "order_number")
    private int orderNumber;
    @Column(name = "batch")
    private int batch;
    @Column(name = "item")
    private int item;
    @Column(name = "amount")
    private int amount;
    @Column(name = "timeneed")
    private long timeneed;
}
