package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CapacityPlanningDTO {

    private int articleNumber;
    private int orderQuantity;
}
