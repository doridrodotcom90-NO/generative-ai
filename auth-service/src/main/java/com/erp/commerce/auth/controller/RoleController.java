package com.erp.commerce.auth.controller;

import com.erp.commerce.auth.dto.RoleDTO;
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
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Role management endpoints")
public class RoleController {

    @GetMapping
    @Operation(summary = "Get all roles", description = "Retrieve list of all roles")
    public ResponseEntity<ApiResponse<List<RoleDTO>>> getAllRoles() {
        // Implementation
        return ResponseEntity.ok(ApiResponse.success(List.of(), "Roles retrieved"));
    }
}
