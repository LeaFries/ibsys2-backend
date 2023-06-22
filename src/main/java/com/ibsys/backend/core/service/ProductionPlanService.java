package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.*;
import com.ibsys.backend.core.repository.*;
import com.ibsys.backend.web.dto.ProductionInPeriodDTO;
import com.ibsys.backend.web.dto.ProductionPlanDTO;
import com.ibsys.backend.web.dto.mapper.ProductionInPeriodMapper;
import com.ibsys.backend.web.dto.mapper.ProductionPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionPlanService {
    private final ProductionPlanRepository productionPlanRepository;
    private final ProductionInPeriodRepository productionInPeriodRepository;
    private final ForecastRepository forecastRepository;
    private final SellDirectRepository sellDirectRepository;
    private final ArticleRepository articleRepository;
    private final ProductionInPeriodMapper productionInPeriodMapper;
    private final ProductionPlanMapper productionPlanMapper;

    public List<ProductionPlan> addProductionPlan(List<ProductionPlanDTO> productionPlanDTOS) {

        List<ProductionPlan> productionPlans = productionPlanRepository.findAll();

        for (int i = 0; i < productionPlans.size(); i++) {
            productionPlans.get(i).setPeriodNplusOne(productionPlanDTOS.get(i).getPeriodNplusOne());
            productionPlans.get(i).setPeriodNplusTwo(productionPlanDTOS.get(i).getPeriodNplusTwo());
            productionPlans.get(i).setPeriodNplusThree(productionPlanDTOS.get(i).getPeriodNplusThree());
        }

        productionPlanRepository.saveAllAndFlush(productionPlans);

        return productionPlans;
    }

    public List<ProductionInPeriod> addProductionInPeriod(List<ProductionInPeriodDTO> productionInPeriodDTOS) {
        List<ProductionInPeriod> newProductionInPeriods = productionInPeriodMapper
                                                        .toProductionInPeriodList(productionInPeriodDTOS);

        List<ProductionInPeriod> productionInPeriods = productionInPeriodRepository.findAll();

        for(int i = 0; i < productionInPeriods.size(); i++) {
            productionInPeriods.get(i).setPeriodNplusOne(newProductionInPeriods.get(i).getPeriodNplusOne());
            productionInPeriods.get(i).setPeriodNplusTwo(newProductionInPeriods.get(i).getPeriodNplusTwo());
            productionInPeriods.get(i).setPeriodNplusThree(newProductionInPeriods.get(i).getPeriodNplusThree());
        }

        productionInPeriodRepository.saveAllAndFlush(productionInPeriods);

        return productionInPeriods;
    }

    public List<ProductionInPeriod> findProductionInPeriods() {
        return productionInPeriodRepository.findAll();
    }

    public List<ProductionPlan> findProductionPlan() {
        List<ProductionPlan> productionPlans = productionPlanRepository.findAll();
        Optional<Forecast> forecast = forecastRepository.findById(1L);

        if (forecast.isEmpty()) {
            productionPlans.get(0).setPeriodN(0);
            productionPlans.get(1).setPeriodN(0);
            productionPlans.get(2).setPeriodN(0);
        }
        else {
            productionPlans.get(0).setPeriodN(forecast.get().getP1());
            productionPlans.get(1).setPeriodN(forecast.get().getP2());
            productionPlans.get(2).setPeriodN(forecast.get().getP3());

        }

        productionPlanRepository.saveAllAndFlush(productionPlans);

        return productionPlans;
    }

    public List<ProductionPlan> findPlannedBikeStock() {
        List<ProductionPlan> forecasts = productionPlanRepository.findAll();
        List<ProductionInPeriod> productionInPeriods = productionInPeriodRepository.findAll();
        int menBikeAmount = articleRepository.findById(1).get().getAmount();
        int womenBikeAmount = articleRepository.findById(2).get().getAmount();
        int childrenBikeAmount = articleRepository.findById(3).get().getAmount();

        List<ProductionPlan> expectedBikeStock = new ArrayList<ProductionPlan>();
        expectedBikeStock.add(new ProductionPlan(1L, 1, menBikeAmount, 0, 0, 0));
        expectedBikeStock.add(new ProductionPlan(2L, 2, womenBikeAmount, 0, 0, 0));
        expectedBikeStock.add(new ProductionPlan(3L, 3, childrenBikeAmount, 0, 0, 0));

        for(int i = 0; i < expectedBikeStock.size(); i++) {
            expectedBikeStock.get(i)
                    .setPeriodNplusOne(
                            expectedBikeStock.get(i).getPeriodN() +
                            productionInPeriods.get(i).getPeriodN() -
                            forecasts.get(i).getPeriodN());

            expectedBikeStock.get(i)
                    .setPeriodNplusTwo(
                            expectedBikeStock.get(i).getPeriodNplusOne() +
                            productionInPeriods.get(i).getPeriodNplusOne() -
                            forecasts.get(i).getPeriodNplusOne()
                    );

            expectedBikeStock.get(i)
                    .setPeriodNplusThree(
                            expectedBikeStock.get(i).getPeriodNplusTwo() +
                                    productionInPeriods.get(i).getPeriodNplusTwo() -
                                    forecasts.get(i).getPeriodNplusTwo()
                    );
        }

        return expectedBikeStock;
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
