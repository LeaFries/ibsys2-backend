package com.ibsys.backend.core.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.ibsys.backend.core.domain.entity.*;
import lombok.Data;

import java.util.List;

@Data
@JsonTypeName("input")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Output {
    @JsonProperty("qualitycontrol")
    private QualityControl qualityControl;
    @JsonProperty("sellwish")
    private List<SellWish> sellWishes;
    @JsonProperty("selldirect")
    private List<SellDirect> sellDirects;
    @JsonProperty("orderlist")
    private List<OrderItem> orderItems;
    @JsonProperty("productionlist")
    private List<Production> productions;
    @JsonProperty("workingtimelist")
    private List<OverTime> workstations;
}
