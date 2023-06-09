package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.OrderItem;
import com.ibsys.backend.web.dto.OrderItemDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toOrderItem(OrderItemDTO productionInPeriodDTO);

    List<OrderItem> toOrderItemList(List<OrderItemDTO> ProductionInPeriodDTOList);
}
