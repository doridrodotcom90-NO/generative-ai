package com.erp.commerce.accounting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "journal_entry_lines")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntryLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "journal_entry_id", nullable = false)
    private Long journalEntryId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Builder.Default
    private BigDecimal debit = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal credit = BigDecimal.ZERO;
}
