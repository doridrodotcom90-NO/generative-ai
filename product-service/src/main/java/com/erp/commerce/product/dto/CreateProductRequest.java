package com.erp.commerce.product.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 255, message = "Product name should be between 2 and 255 characters")
    private String name;

    @Size(max = 100, message = "Model number should not exceed 100 characters")
    private String modelNo;

    @Size(max = 100, message = "Barcode should not exceed 100 characters")
    private String barcode;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Brand ID is required")
    private Long brandId;

    @NotNull(message = "Buy price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Buy price must be greater than 0")
    private BigDecimal buyPrice;

    @NotNull(message = "Sale price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Sale price must be greater than 0")
    private BigDecimal salePrice;

    @Min(value = 0, message = "Warranty months should be >= 0")
    private Integer warrantyMonths;
}
