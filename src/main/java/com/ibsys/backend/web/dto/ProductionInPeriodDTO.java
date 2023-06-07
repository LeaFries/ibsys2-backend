package com.ibsys.backend.web.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProductionInPeriodDTO {
    @Positive
    private int article;

    @Positive
    private int periodN;

    @Positive
    private int periodNplusOne;

    @Positive
    private int periodNplusTwo;

    @Positive
    private int periodNplusThree;
}
