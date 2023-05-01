package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.entity.Forecast;
import com.ibsys.backend.core.repository.ForecastRepository;
import com.ibsys.backend.core.repository.PlannedWarehouseStockRepository;
import com.ibsys.backend.core.repository.ProductionPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InputService {
    private final ForecastRepository forecastRepository;
    private final ProductionPlanRepository productionPlanRepository;
    private final PlannedWarehouseStockRepository plannedWarehouseStockRepository;

    public void saveProductionPlan(int p1, int p2, int p3) {
        Forecast forecastP1 = forecastRepository.save(new Forecast(1, p1));
        Forecast forecastP2 = forecastRepository.save(new Forecast(2, p2));
        Forecast forecastP3 = forecastRepository.save(new Forecast(3, p3));

        log.debug("saved " + forecastP1);
        log.debug("saved " + forecastP2);
        log.debug("saved " + forecastP3);

    }


}
