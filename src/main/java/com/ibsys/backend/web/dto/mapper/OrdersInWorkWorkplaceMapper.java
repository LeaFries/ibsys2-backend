package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.OrdersInWorkWorkplace;
import com.ibsys.backend.web.dto.OrdersInWorkWorkplaceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdersInWorkWorkplaceMapper {
    OrdersInWorkWorkplace toOrdersInWorkWorkplace(final OrdersInWorkWorkplaceDTO ordersInWorkWorkplaceDTO);
}
