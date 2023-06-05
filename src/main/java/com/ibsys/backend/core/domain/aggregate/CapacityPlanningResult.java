package com.ibsys.backend.core.domain.aggregate;

import com.ibsys.backend.core.domain.entity.OverTime;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CapacityPlanningResult {

    private Map<Integer, Integer> newCapacity_reqs;
    private Map<Integer, Integer> newSetUpTime;
    private Map<Integer, Integer> behindScheduleCapacity;
    private Map<Integer, Integer> behindScheduleSetUpTime;
    private Map<Integer, Integer> totalCapacityRequirement;
    private List<OverTime> workstationsWithOverTime;
}
