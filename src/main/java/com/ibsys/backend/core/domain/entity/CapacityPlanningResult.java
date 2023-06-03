package com.ibsys.backend.core.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class CapacityPlanningResult {

    private int newCapacity_req;
    private int newSetUpTime;
    private int behindScheduleCapacity;
    private int behindScheduleSetUpTime;
    private int totalCapacityRequirement;
    private List<OverTime> workstationsWithOverTime;
}
