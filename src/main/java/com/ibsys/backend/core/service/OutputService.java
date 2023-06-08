package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.aggregate.Output;
import com.ibsys.backend.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutputService {
    private final OrderRepository orderRepository;
    private final ProductionRepository productionRepository;
    private final QualityControlRepository qualityControlRepository;
    private final SellDirectRepository sellDirectRepository;
    private final SellWishRepository sellWishRepository;
    private final WorkingTimeRepository workingTimeRepository;

    public Output findInput() {
        Output output = new Output();

        output.setOrders(orderRepository.findAll());
        output.setProductions(productionRepository.findAll());
        output.setQualityControl(qualityControlRepository.findById(1L).get());
        output.setSellDirects(sellDirectRepository.findAll());
        output.setSellWishes(sellWishRepository.findAll());
        output.setWorkstationsWithOverTime(workingTimeRepository.findAll());

        return output;
    }
}
