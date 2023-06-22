package com.ibsys.backend.web.dto;

import com.ibsys.backend.core.domain.entity.FutureInwardStockmovement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InputDTO {
    private int game;
    private int group;
    private int period;
    private ForecastDTO forecast;
    private WarehousestockDTO warehousestock;
    private List<OrderDTO> inwardstockmovement;
    private List<FutureInwardStockmovement> futureinwardstockmovement;
    private IdletimecostsDTO idletimecosts;
    private List<WorkplaceDTO> waitinglistworkstations;
    private List<MissingPartDTO> waitingliststock;
    private List<OrdersInWorkWorkplaceDTO> ordersinwork;
    private List<OrderDTO> completedorders;
    private CycletimesDTO cycletimes;
}
