package com.erp.commerce.accounting.service;

import com.erp.commerce.accounting.dto.JournalEntryDTO;
import com.erp.commerce.accounting.entity.JournalEntry;
import com.erp.commerce.accounting.repository.JournalEntryRepository;
import com.erp.commerce.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;

    public JournalEntryDTO getJournalEntryById(Long id) {
        JournalEntry entry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal entry not found"));
        return mapToDTO(entry);
    }

    public List<JournalEntryDTO> getEntriesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<JournalEntry> entries = journalEntryRepository.findByEntryDateBetween(startDate, endDate);
        return entries.stream().map(this::mapToDTO).toList();
    }

    public List<JournalEntryDTO> getEntriesByReference(String referenceType, Long referenceId) {
        List<JournalEntry> entries = journalEntryRepository.findByReferenceTypeAndReferenceId(referenceType, referenceId);
        return entries.stream().map(this::mapToDTO).toList();
    }

    private JournalEntryDTO mapToDTO(JournalEntry entry) {
        return JournalEntryDTO.builder()
                .id(entry.getId())
                .entryDate(entry.getEntryDate())
                .referenceType(entry.getReferenceType())
                .referenceId(entry.getReferenceId())
                .memo(entry.getMemo())
                .createdBy(entry.getCreatedBy())
                .createdAt(entry.getCreatedAt())
                .build();
    }
}
