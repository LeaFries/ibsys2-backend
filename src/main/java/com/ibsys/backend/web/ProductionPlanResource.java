package com.ibsys.backend.web;

import com.ibsys.backend.core.domain.entity.ProductionInPeriod;
import com.ibsys.backend.core.domain.entity.ProductionPlan;
import com.ibsys.backend.core.domain.entity.SellDirect;
import com.ibsys.backend.core.service.ProductionPlanService;
import com.ibsys.backend.web.dto.ProductionInPeriodDTO;
import com.ibsys.backend.web.dto.ProductionPlanDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/productionplan")
public class ProductionPlanResource {

    private final ProductionPlanService productionPlanService;

    @Operation(summary = "Provides the initial forecast information")
    @GetMapping("/forecast")
    public ResponseEntity<List<ProductionPlan>> findInitialProductionPlan() {
        return ResponseEntity.ok(productionPlanService.findProductionPlan());
    }

    @Operation(summary = "This endpoint is supposed to be manually filled with the production plan forecast")
    @PostMapping("/forecast/new")
    public ResponseEntity<List<ProductionPlan>> addProductionPlan(@RequestBody List<ProductionPlanDTO> productionPlans) {

        return ResponseEntity.ok(productionPlanService.addProductionPlan(productionPlans));
    }

    @Operation(summary = "This endpoint is supposed to be manually filled with the planned production in period")
    @PostMapping("/production")
    public ResponseEntity<List<ProductionInPeriod>> addProductionInPeriod(
            @RequestBody List<ProductionInPeriodDTO> productionInPeriodDTOS) {

        return ResponseEntity.ok(productionPlanService.addProductionInPeriod(productionInPeriodDTOS));
    }

    @Operation(summary = "Shows the calculation of the planned bike stock")
    @GetMapping("/plannedbikestock")
    public ResponseEntity<List<ProductionPlan>> findPlannedBikeStock() {
        return ResponseEntity.ok(productionPlanService.findPlannedBikeStock());
    }

    @Operation(summary = "This endpoint is supposed to be manually filled with the sell direct")
    @PostMapping("/selldirect")
    public ResponseEntity<List<SellDirect>> addSellDirect(
            @RequestBody List<SellDirect> sellDirects) {

        return ResponseEntity.ok(productionPlanService.addSellDirect(sellDirects));
    }
}
