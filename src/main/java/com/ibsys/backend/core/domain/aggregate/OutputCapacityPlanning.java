package com.ibsys.backend.core.domain.aggregate;

import com.ibsys.backend.core.domain.entity.OverTime;
import com.ibsys.backend.core.domain.entity.CapacityPlanColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class OutputCapacityPlanning {

    private List<CapacityPlanColumn> workingTimePlan;
    private List<OverTime> workingTimes;

}
