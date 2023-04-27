package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ForecastDTO {
    private int p1;
    private int p2;
    private int p3;
}
