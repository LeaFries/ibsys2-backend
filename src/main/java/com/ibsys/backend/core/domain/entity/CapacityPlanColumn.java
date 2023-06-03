package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "CapacityPlan")
@NoArgsConstructor
@AllArgsConstructor
public class CapacityPlanColumn {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int articleNumber;

    private int workstation;

    private int workingTime;

    private int setUpTime;

    public CapacityPlanColumn(int articleNumber, int workstationNumber, int workingTime, int setUpTime) {
        this.articleNumber = articleNumber;
        this.workstation = workstationNumber;
        this.workingTime = workingTime;
        this.setUpTime = setUpTime;
    }
}
