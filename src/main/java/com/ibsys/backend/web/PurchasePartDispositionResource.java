package com.ibsys.backend.web;

import com.ibsys.backend.core.domain.entity.PurchasePartDisposition;
import com.ibsys.backend.core.service.OutputService;
import com.ibsys.backend.core.service.PurchasePartDispositionService;
import com.ibsys.backend.web.dto.OrderItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchasepartdisposition")
public class PurchasePartDispositionResource {
    private final PurchasePartDispositionService purchasePartDispositionService;
    private final OutputService outputService;

    @Operation(summary = "Provides the purchase part disposition")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PurchasePartDisposition>> findInput() {
        return ResponseEntity.ok(purchasePartDispositionService.findPurchasePartDisposition());
    }

    @Operation(summary = "This endpoint feeds our output data with the orders")
    @PostMapping("/output")
    public ResponseEntity<List<OrderItemDTO>> addOrderItem(@RequestBody List<OrderItemDTO> orderItemDTOS) {

        return ResponseEntity.ok(outputService.addOrderItems(orderItemDTOS));
    }
}
