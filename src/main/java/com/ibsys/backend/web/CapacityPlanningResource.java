package com.ibsys.backend.web;

import com.ibsys.backend.core.service.DispositionEigenfertigungService;
import com.ibsys.backend.web.dto.ArticleDTO;
import com.ibsys.backend.web.dto.CapacityPlanningDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/capacity-planning")
public class CapacityPlanningResource {

    private final DispositionEigenfertigungService dispositionEigenfertigungService;

    @Operation(summary = "Starts the Capacity Planning")
    @PostMapping
    public void startDispositionEigenfertigung(@RequestBody List<CapacityPlanningDTO> capacityPlanningDTO) {

    }

}
