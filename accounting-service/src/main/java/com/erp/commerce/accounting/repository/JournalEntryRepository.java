package com.erp.commerce.accounting.repository;

import com.erp.commerce.accounting.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByEntryDateBetween(LocalDate startDate, LocalDate endDate);
    List<JournalEntry> findByReferenceTypeAndReferenceId(String referenceType, Long referenceId);
}
