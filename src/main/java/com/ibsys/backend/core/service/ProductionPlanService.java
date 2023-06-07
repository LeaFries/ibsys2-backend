package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.ProductionPlan;
import com.ibsys.backend.core.repository.ProductionPlanRepository;
import com.ibsys.backend.web.dto.ProductionPlanDTO;
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
    private final ProductionPlanMapper productionPlanMapper;

    public List<ProductionPlan> addProductionPlan(List<ProductionPlanDTO> productionPlanDTOS) {

        List<ProductionPlan> productionPlans = productionPlanMapper.toProductionPlanList(productionPlanDTOS);

        List<ProductionPlan> newProductionplans = productionPlanRepository.saveAllAndFlush(productionPlans);

        return newProductionplans;
    }
}
