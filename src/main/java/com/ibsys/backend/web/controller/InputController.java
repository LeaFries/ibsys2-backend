package com.ibsys.backend.web.controller;

import com.ibsys.backend.core.domain.aggregate.Input;
import com.ibsys.backend.core.service.InputService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/input")
@RequiredArgsConstructor
public class InputController {
    private final InputService inputService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Input> findInput() {
        return ResponseEntity.ok(inputService.findInput());
    }
}
