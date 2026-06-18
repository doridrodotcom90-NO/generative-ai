package com.erp.commerce.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String phone;
    private String name;
    private String status;
    private Long roleId;
    private String roleName;
    private Long branchId;
    private String branchName;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private Set<String> permissions;
}
