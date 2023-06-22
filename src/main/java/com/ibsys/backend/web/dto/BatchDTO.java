package com.ibsys.backend.web.dto;

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
public class BatchDTO {
    private int id;
    private int amount;
    private long cycletime;
    private String cost;
}
