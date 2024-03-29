package com.ibsys.backend.web;

import com.ibsys.backend.core.domain.entity.KQuantityNeed;
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

    @Operation(summary = "4. Provides the purchase part disposition")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PurchasePartDisposition>> findInput() {
        return ResponseEntity.ok(purchasePartDispositionService.findPurchasePartDisposition());
    }

    @Operation(summary = "4.1 Provides the K-Quantity-Needs for each bike")
    @GetMapping("/quantityneed")
    public ResponseEntity<List<KQuantityNeed>> findKQuantityNeeds() {
        return ResponseEntity.ok(purchasePartDispositionService.findKquantityNeeds());
    }

    @Operation(summary = "5. This endpoint feeds our output data with the orders")
    @PostMapping("/output")
    public ResponseEntity<List<OrderItemDTO>> addOrderItem(@RequestBody List<OrderItemDTO> orderItemDTOS) {

        return ResponseEntity.ok(outputService.addOrderItems(orderItemDTOS));
    }
}
