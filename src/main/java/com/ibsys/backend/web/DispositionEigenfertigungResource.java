package com.ibsys.backend.web;

import com.ibsys.backend.core.service.DispositionEigenfertigungService;
import com.ibsys.backend.web.dto.DispositionEigenfertigungInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/disposition-eigenfertigung")
@Slf4j
public class DispositionEigenfertigungResource {

    private final DispositionEigenfertigungService dispositionEigenfertigungService;

    @Operation(summary = "Starts the Disposition Eigenfertigung")
    @PostMapping
    public void startDispositionEigenfertigung(@RequestBody final DispositionEigenfertigungInputDTO inputDTOS) {
        dispositionEigenfertigungService.dispositionEigenfertigungStart(inputDTOS);
    }

    @GetMapping
    public ResponseEntity<DispositionEigenfertigungInputDTO> test() {
        return ResponseEntity.ok(DispositionEigenfertigungInputDTO.builder()
                        .geplanterSicherheitsbestand(new HashMap<>(
                                Collections.singletonMap(1, 1)
                        ))
                .build());
    }

}
