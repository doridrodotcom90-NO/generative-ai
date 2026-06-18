package com.erp.commerce.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntryDTO {
    private Long id;
    private LocalDate entryDate;
    private String referenceType;
    private Long referenceId;
    private String memo;
    private Long createdBy;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private LocalDate createdAt;
}
