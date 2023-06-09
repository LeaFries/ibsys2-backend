package com.ibsys.backend.core.domain.aggregate;

import com.ibsys.backend.core.domain.entity.CapacityPlanColumn;
import lombok.Data;

import java.util.List;

@Data
public class OutputCapacityPlanning {

    private List<CapacityPlanColumn> workingTimePlan;
    private CapacityPlanningResult capacityPlanningResult;

}
