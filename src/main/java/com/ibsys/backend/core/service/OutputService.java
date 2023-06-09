package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.aggregate.Output;
import com.ibsys.backend.core.domain.entity.OrderItem;
import com.ibsys.backend.core.repository.*;
import com.ibsys.backend.web.dto.OrderItemDTO;
import com.ibsys.backend.web.dto.mapper.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutputService {
    private final OrderRepository orderRepository;
    private final ProductionRepository productionRepository;
    private final QualityControlRepository qualityControlRepository;
    private final SellDirectRepository sellDirectRepository;
    private final SellWishRepository sellWishRepository;
    private final WorkingTimeRepository workingTimeRepository;

    private final OrderItemMapper orderItemMapper;

    public Output findInput() {
        Output output = new Output();

        output.setOrderItems(orderRepository.findAll());
        output.setProductions(productionRepository.findAll());
        output.setQualityControl(qualityControlRepository.findById(1L).get());
        output.setSellDirects(sellDirectRepository.findAll());
        output.setSellWishes(sellWishRepository.findAll());
        output.setWorkstations(workingTimeRepository.findAll());

        return output;
    }

    @Transactional
    public List<OrderItemDTO> addOrderItems(List<OrderItemDTO> orderItemDTOS) {
        List<OrderItem> orderItems = orderItemMapper.toOrderItemList(orderItemDTOS);

        orderRepository.saveAllAndFlush(orderItems);

        return orderItemDTOS;
    }
}
