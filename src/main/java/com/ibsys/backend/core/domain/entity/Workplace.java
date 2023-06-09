package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workplace")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Workplace {

    @Id
    private int id;

    @Column(name = "setupevent")
    private int setupevent;

    @Column(name = "idletime")
    private int idletime;

    @Column(name = "wageidletimecosts")
    private String wageidletimecosts;

    @Column(name = "wagecosts")
    private String wagecosts;

    @Column(name = "machineidletimecosts")
    private String machineidletimecosts;

    @Column(name = "timeneed")
    private int timeneed;

    @Column(name = "period")
    private int period;

    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "bach")
    private int batch;

    @Column(name = "item")
    private int item;

    @Column(name = "amount")
    private int amount;

}
