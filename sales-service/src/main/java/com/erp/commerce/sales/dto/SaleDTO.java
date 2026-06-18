package com.erp.commerce.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long id;
    private String invoiceNo;
    private Long customerId;
    private String customerName;
    private Long branchId;
    private String saleType;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal grandTotal;
    private String paymentStatus;
    private String status;
    private LocalDateTime saleDate;
    private LocalDateTime createdAt;
}
