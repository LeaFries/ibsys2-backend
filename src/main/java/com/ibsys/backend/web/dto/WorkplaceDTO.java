package com.ibsys.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class WorkplaceDTO {
    private int id;
    private int setupevents;
    private int idletime;
    private String wageidletimecosts;
    private String wagecosts;
    private String machineidletimecosts;
    private long timeneed;
    private List<WaitinglistDTO> waitinglist;
    private int period;
    private int order;
    private int batch;
    private int item;
    private int amount;
}
