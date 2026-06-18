package com.erp.commerce.sales.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSaleRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Branch ID is required")
    private Long branchId;

    @NotNull(message = "Sold by user ID is required")
    private Long soldBy;

    @NotBlank(message = "Sale type is required")
    private String saleType; // CASH, EMI, MIXED

    @DecimalMin(value = "0.0", inclusive = false, message = "Subtotal must be greater than 0")
    private BigDecimal subtotal;

    @DecimalMin(value = "0.0", message = "Discount cannot be negative")
    private BigDecimal discount;

    @DecimalMin(value = "0.0", message = "Tax cannot be negative")
    private BigDecimal tax;

    @NotEmpty(message = "At least one sale item is required")
    private List<SaleItemDTO> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleItemDTO {
        @NotNull(message = "Product ID is required")
        private Long productId;

        private Long productUnitId;

        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer qty;

        @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
        private BigDecimal unitPrice;

        @DecimalMin(value = "0.0", message = "Discount cannot be negative")
        private BigDecimal discount;
    }
}
