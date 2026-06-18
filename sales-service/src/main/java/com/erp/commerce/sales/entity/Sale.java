package com.erp.commerce.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_no", unique = true, nullable = false)
    private String invoiceNo;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "sold_by", nullable = false)
    private Long soldBy;

    @Column(name = "sale_type")
    @Builder.Default
    private String saleType = "CASH";

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal tax = BigDecimal.ZERO;

    @Column(name = "grand_total", nullable = false)
    private BigDecimal grandTotal;

    @Column(name = "payment_status")
    @Builder.Default
    private String paymentStatus = "UNPAID";

    @Column(nullable = false)
    @Builder.Default
    private String status = "DRAFT";

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
