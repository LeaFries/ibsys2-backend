package com.ibsys.backend.web.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductionInPeriodDTO {
    @Positive
    private int article;

    private int periodNplusOne;

    private int periodNplusTwo;

    private int periodNplusThree;
}
