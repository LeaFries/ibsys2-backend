package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class WaitingliststockWaitinglistDTO {
    private int period;
    private int orderNumber;
    private int firstbatch;
    private int lastbatch;
    private int item;
    private int amount;
    private int timeNeed;
}
