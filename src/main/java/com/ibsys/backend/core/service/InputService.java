package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.aggregate.Input;
import com.ibsys.backend.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InputService {
    private final OrderRepository orderRepository;
    private final ProductionRepository productionRepository;
    private final QualityControlRepository qualityControlRepository;
    private final SellDirectRepository sellDirectRepository;
    private final SellWishRepository sellWishRepository;
    private final WorkingTimeRepository workingTimeRepository;

    public Input findInput() {
        Input input = new Input();

        input.setOrders(orderRepository.findAll());
        input.setProductions(productionRepository.findAll());
        input.setQualityControl(qualityControlRepository.findById(1L).get());
        input.setSellDirects(sellDirectRepository.findAll());
        input.setSellWishes(sellWishRepository.findAll());
        input.setWorkingTimes(workingTimeRepository.findAll());

        return input;
    }
}
