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
public class OrdersInWorkWorkplaceDTO {
    private int id;
    private int period;
    private int orderNumber;
    private int batch;
    private int item;
    private int amount;
    private long timeneed;
}
