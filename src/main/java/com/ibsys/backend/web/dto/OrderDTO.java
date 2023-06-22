package com.ibsys.backend.web.dto;

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
public class OrderDTO {

    private int orderperiod;
    private int period;
    private int item;
    private int quantity;
    private String cost;
    private String averageunitcosts;
    private int id;
    private int mode;
    private int article;
    private long amount;
    private long time;
    private String materialcosts;
    private String ordercosts;
    private String entirecosts;
    private String piececosts;
    private List<BatchDTO> batch;
    private String starttime;
    private String finishtime;
    private String cycletimemin;
    private String cycletimefactor;

}
