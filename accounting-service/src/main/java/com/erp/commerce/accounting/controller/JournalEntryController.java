package com.erp.commerce.accounting.controller;

import com.erp.commerce.accounting.dto.JournalEntryDTO;
import com.erp.commerce.accounting.service.JournalEntryService;
import com.erp.commerce.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/journal-entries")
@RequiredArgsConstructor
@Tag(name = "Accounting", description = "Accounting and Journal Entry endpoints")
public class JournalEntryController {
    private final JournalEntryService journalEntryService;

    @GetMapping("/{id}")
    @Operation(summary = "Get journal entry", description = "Retrieve journal entry details")
    public ResponseEntity<ApiResponse<JournalEntryDTO>> getJournalEntry(@PathVariable Long id) {
        JournalEntryDTO entry = journalEntryService.getJournalEntryById(id);
        return ResponseEntity.ok(ApiResponse.success(entry, "Journal entry retrieved successfully"));
    }

    @GetMapping("/by-date-range")
    @Operation(summary = "Get entries by date range", description = "Retrieve journal entries within date range")
    public ResponseEntity<ApiResponse<List<JournalEntryDTO>>> getEntriesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<JournalEntryDTO> entries = journalEntryService.getEntriesByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(entries, "Journal entries retrieved successfully"));
    }
}
