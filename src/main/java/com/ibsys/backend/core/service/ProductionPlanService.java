package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.ProductionInPeriod;
import com.ibsys.backend.core.domain.entity.ProductionPlan;
import com.ibsys.backend.core.domain.entity.SellDirect;
import com.ibsys.backend.core.repository.ProductionInPeriodRepository;
import com.ibsys.backend.core.repository.ProductionPlanRepository;
import com.ibsys.backend.core.repository.SellDirectRepository;
import com.ibsys.backend.web.dto.ProductionInPeriodDTO;
import com.ibsys.backend.web.dto.ProductionPlanDTO;
import com.ibsys.backend.web.dto.mapper.ProductionInPeriodMapper;
import com.ibsys.backend.web.dto.mapper.ProductionPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionPlanService {
    private final ProductionPlanRepository productionPlanRepository;
    private final ProductionInPeriodRepository productionInPeriodRepository;
    private final SellDirectRepository sellDirectRepository;
    private final ProductionInPeriodMapper productionInPeriodMapper;
    private final ProductionPlanMapper productionPlanMapper;

    public List<ProductionPlan> addProductionPlan(List<ProductionPlanDTO> productionPlanDTOS) {

        List<ProductionPlan> productionPlans = productionPlanMapper.toProductionPlanList(productionPlanDTOS);

        List<ProductionPlan> newProductionplans = productionPlanRepository.saveAllAndFlush(productionPlans);

        return newProductionplans;
    }

    public List<ProductionInPeriod> addProductionInPeriod(List<ProductionInPeriodDTO> productionInPeriodDTOS) {
        List<ProductionInPeriod> newProductionInPeriods = productionInPeriodMapper
                                                        .toProductionInPeriodList(productionInPeriodDTOS);

        List<ProductionInPeriod> productionInPeriods = productionInPeriodRepository.findAll();

        for(int i = 0; i < productionInPeriods.size(); i++) {
            productionInPeriods.get(i).setPeriodN(newProductionInPeriods.get(i).getPeriodN());
            productionInPeriods.get(i).setPeriodNplusOne(newProductionInPeriods.get(i).getPeriodNplusOne());
            productionInPeriods.get(i).setPeriodNplusTwo(newProductionInPeriods.get(i).getPeriodNplusTwo());
            productionInPeriods.get(i).setPeriodNplusThree(newProductionInPeriods.get(i).getPeriodNplusThree());
        }

        productionInPeriodRepository.saveAllAndFlush(productionInPeriods);

        return productionInPeriods;
    }

    public List<SellDirect> addSellDirect(List<SellDirect> newSellDirects) {
        List<SellDirect> sellDirects = sellDirectRepository.findAll();

        for (int i = 0; i < newSellDirects.size(); i++) {
            sellDirects.get(i).setPenalty(newSellDirects.get(i).getPenalty());
            sellDirects.get(i).setPrice(newSellDirects.get(i).getPrice());
            sellDirects.get(i).setQuantity(newSellDirects.get(i).getQuantity());
        }

        sellDirectRepository.saveAllAndFlush(sellDirects);

        return sellDirects;
    }
}
