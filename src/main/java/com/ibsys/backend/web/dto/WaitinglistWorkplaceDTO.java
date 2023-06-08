package com.ibsys.backend.web.dto;

import com.ibsys.backend.core.domain.entity.Workplace;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class WaitinglistWorkplaceDTO {

    private int period;
    private int order;
    private int firstbatch;
    private int lastbatch;
    private int item;
    private int amount;
    private long timeNeed;
    private Workplace workplace;

}
