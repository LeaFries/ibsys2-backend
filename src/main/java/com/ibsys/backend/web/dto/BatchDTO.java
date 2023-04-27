package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class BatchDTO {
    private int id;
    private int amount;
    private long cycletime;
    private String cost;
}
