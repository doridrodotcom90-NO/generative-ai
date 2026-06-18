package com.erp.commerce.sales.controller;

import com.erp.commerce.sales.dto.CreateSaleRequest;
import com.erp.commerce.sales.dto.SaleDTO;
import com.erp.commerce.sales.service.SalesService;
import com.erp.commerce.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Sales management endpoints")
public class SalesController {
    private final SalesService salesService;

    @PostMapping
    @Operation(summary = "Create sale", description = "Create a new sale")
    public ResponseEntity<ApiResponse<SaleDTO>> createSale(@Valid @RequestBody CreateSaleRequest request) {
        SaleDTO sale = salesService.createSale(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(sale, "Sale created successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sale by ID", description = "Retrieve sale details")
    public ResponseEntity<ApiResponse<SaleDTO>> getSale(@PathVariable Long id) {
        SaleDTO sale = salesService.getSaleById(id);
        return ResponseEntity.ok(ApiResponse.success(sale, "Sale retrieved successfully"));
    }

    @PostMapping("/{id}/confirm")
    @Operation(summary = "Confirm sale", description = "Confirm a sale order")
    public ResponseEntity<ApiResponse<?>> confirmSale(@PathVariable Long id) {
        salesService.confirmSale(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Sale confirmed successfully"));
    }
}
