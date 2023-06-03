package com.ibsys.backend.web;

import com.ibsys.backend.core.service.CapacityPlanningService;
import com.ibsys.backend.web.dto.InputCapacityPlanningDTO;
import com.ibsys.backend.core.domain.aggregate.OutputCapacityPlanning;
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
@RequestMapping("/api/capacity-planning")
public class CapacityPlanningResource {

    private final CapacityPlanningService capacityPlanningService;

    @Operation(summary = "Starts the Capacity Planning")
    @PostMapping
    public ResponseEntity<String> startCapacityPlanning(@RequestBody List<InputCapacityPlanningDTO> inputCapacityPlanningDTO) {
        capacityPlanningService.calculateCapacityPlan(inputCapacityPlanningDTO);
        return ResponseEntity.ok(capacityPlanningService.getOutput());
    }

}
