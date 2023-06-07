package com.ibsys.backend.web;

import com.ibsys.backend.core.domain.entity.ProductionPlan;
import com.ibsys.backend.core.service.ProductionPlanService;
import com.ibsys.backend.web.dto.InputDTO;
import com.ibsys.backend.web.dto.ProductionPlanDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/productionplan")
public class ProductionPlanResource {

    private final ProductionPlanService productionPlanService;

    @Operation(summary = "This endpoint is supposed to be manually filled with the production plan")
    @PostMapping
    public ResponseEntity<List<ProductionPlan>> start(@RequestBody List<ProductionPlanDTO> productionPlans) {

        return ResponseEntity.ok(productionPlanService.addProductionPlan(productionPlans));
    }
}
