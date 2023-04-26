package com.ibsys.backend.core.domain.aggregate;

import com.ibsys.backend.core.domain.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class Input {
    private QualityControl qualityControl;
    private List<SellWish> sellWishes;
    private List<SellDirect> sellDirects;
    private List<Order> orders;
    private List<Production> productions;
    private List<WorkingTime> workingTimes;
}
