package com.ibsys.backend.web;

import com.ibsys.backend.core.domain.entity.PurchasePartDisposition;
import com.ibsys.backend.core.service.PurchasePartDispositionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchasepartdisposition")
public class PurchasePartDispositionResource {
    private final PurchasePartDispositionService purchasePartDispositionService;

    @Operation(summary = "Provides the purchase part disposition")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PurchasePartDisposition>> findInput() {
        return ResponseEntity.ok(purchasePartDispositionService.findPurchasePartDisposition());
    }
}
