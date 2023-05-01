package com.ibsys.backend.web;

import com.ibsys.backend.core.domain.aggregate.Output;
import com.ibsys.backend.core.service.OutputService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/output")
@RequiredArgsConstructor
public class OutputResource {
    private final OutputService outputService;

    @Operation(summary = "Provides the final result of the production plan")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Output> findInput() {
        return ResponseEntity.ok(outputService.findInput());
    }
}
