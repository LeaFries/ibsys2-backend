package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "waitinglist")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Waitinglist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "period")
    private int period;
    @Column(name = "order_number")
    private int order;
    @Column(name = "firstbatch")
    private int firstbatch;
    @Column(name = "lastbatch")
    private int lastbatch;
    @Column(name = "item")
    private int item;
    @Column(name = "amount")
    private int amount;
    @Column(name = "timeneed")
    private int timeneed;
    @ManyToOne
    @JoinColumn(name = "workplace_fk")
    private Workplace workplace;
}
