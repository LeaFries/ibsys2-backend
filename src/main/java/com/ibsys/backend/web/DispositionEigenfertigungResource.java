package com.ibsys.backend.web;

import com.ibsys.backend.core.service.DispositionEigenfertigungService;
import com.ibsys.backend.web.dto.DispositionEigenfertigungInputDTO;
import com.ibsys.backend.web.dto.DispositionEigenfertigungResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/disposition-eigenfertigung")
@Slf4j
public class DispositionEigenfertigungResource {

    private final DispositionEigenfertigungService dispositionEigenfertigungService;

    @Operation(summary = "1. Starts the Disposition Eigenfertigung")
    @PostMapping
    public ResponseEntity<List<DispositionEigenfertigungResultDTO>> startDispositionEigenfertigung(@RequestBody final DispositionEigenfertigungInputDTO inputDTOS) {
        List<DispositionEigenfertigungResultDTO> result = dispositionEigenfertigungService.dispositionEigenfertigungStart(inputDTOS);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<DispositionEigenfertigungResultDTO>> test() {
        return ResponseEntity.ok(dispositionEigenfertigungService.get());
    }

}
