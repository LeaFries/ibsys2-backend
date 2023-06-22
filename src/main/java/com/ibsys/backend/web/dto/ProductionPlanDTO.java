package com.ibsys.backend.web.dto;

import jakarta.persistence.Column;
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
public class ProductionPlanDTO {
    @Positive
    private int article;

    @Positive
    private int periodNplusOne;

    @Positive
    private int periodNplusTwo;

    @Positive
    private int periodNplusThree;
}
