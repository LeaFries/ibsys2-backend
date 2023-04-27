package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class InputDTO {
    private int game;
    private int group;
    private int period;
    private ForecastDTO forecast;
    private WarehousestockDTO warehousestock;
    private List<OrderDTO> inwardstockmovement;
    private List<OrderDTO> futureinwardstockmovement;
    private IdletimecostsDTO idletimecosts;
    private List<WorkplaceDTO> waitinglistworkstations;
    private List<WorkplaceDTO> workplace;
    private List<WorkplaceDTO> ordersinwork;
    private List<OrderDTO> completedorders;
    private CycletimesDTO cycletimes;
}
