package com.erp.commerce.auth.controller;

import com.erp.commerce.auth.dto.BranchDTO;
import com.erp.commerce.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
@Tag(name = "Branches", description = "Branch management endpoints")
public class BranchController {

    @GetMapping
    @Operation(summary = "Get all branches", description = "Retrieve list of all branches")
    public ResponseEntity<ApiResponse<List<BranchDTO>>> getAllBranches() {
        // Implementation
        return ResponseEntity.ok(ApiResponse.success(List.of(), "Branches retrieved"));
    }
}
