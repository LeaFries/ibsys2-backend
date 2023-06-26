package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.aggregate.Output;
import com.ibsys.backend.core.domain.entity.Forecast;
import com.ibsys.backend.core.domain.entity.OrderItem;
import com.ibsys.backend.core.domain.entity.Production;
import com.ibsys.backend.core.domain.entity.SellWish;
import com.ibsys.backend.core.repository.*;
import com.ibsys.backend.web.dto.OrderItemDTO;
import com.ibsys.backend.web.dto.mapper.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutputService {
    private final OrderRepository orderRepository;
    private final ProductionRepository productionRepository;
    private final QualityControlRepository qualityControlRepository;
    private final SellDirectRepository sellDirectRepository;
    private final SellWishRepository sellWishRepository;
    private final WorkingTimeRepository workingTimeRepository;
    private final ForecastRepository forecastRepository;

    private final OrderItemMapper orderItemMapper;

    public Output findInput() {
        Output output = new Output();

        output.setOrderItems(orderRepository.findAll());
        List<Production> productionList = productionRepository.findAll();
        List<Production> result = new ArrayList<>();
        productionList.stream().forEach(production -> {
            if(production.getQuantity() > 0) {
                result.add(production);
            }
        });

        output.setProductions(result);
        output.setQualityControl(qualityControlRepository.findById(1L).get());
        output.setSellDirects(sellDirectRepository.findAll());

        feedSellWish();

        output.setSellWishes(sellWishRepository.findAll());
        output.setWorkstations(workingTimeRepository.findAll());

        return output;
    }

    @Transactional
    public List<OrderItemDTO> addOrderItems(List<OrderItemDTO> orderItemDTOS) {
        orderRepository.deleteAll();

        List<OrderItem> orderItems = orderItemMapper.toOrderItemList(orderItemDTOS);

        orderRepository.saveAllAndFlush(orderItems);

        return orderItemDTOS;
    }

    private void feedSellWish() {
        Optional<Forecast> forecast = forecastRepository.findById(1L);

        if (forecast.isEmpty()) {
            return;
        }

        List<SellWish> sellWishes = sellWishRepository.findAll();

        sellWishes.get(0).setQuantity(forecast.get().getP1());
        sellWishes.get(1).setQuantity(forecast.get().getP2());
        sellWishes.get(2).setQuantity(forecast.get().getP3());

        sellWishRepository.saveAllAndFlush(sellWishes);
    }
}
