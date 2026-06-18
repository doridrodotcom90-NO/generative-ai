package com.erp.commerce.auth.service;

import com.erp.commerce.auth.dto.CreateUserRequest;
import com.erp.commerce.auth.dto.UserDTO;
import com.erp.commerce.auth.entity.Role;
import com.erp.commerce.auth.entity.Branch;
import com.erp.commerce.auth.entity.User;
import com.erp.commerce.auth.repository.UserRepository;
import com.erp.commerce.auth.repository.RoleRepository;
import com.erp.commerce.auth.repository.BranchRepository;
import com.erp.commerce.common.exception.BusinessException;
import com.erp.commerce.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO createUser(CreateUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Email already exists");
        }
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new BusinessException("Phone already exists");
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        User user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .name(request.getName())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .branch(branch)
                .status("ACTIVE")
                .failedLoginAttempts(0)
                .accountLocked(false)
                .build();

        user = userRepository.save(user);
        log.info("User created: {}", user.getId());

        return mapToDTO(user);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getStatus() != null) user.setStatus(dto.getStatus());

        user = userRepository.save(user);
        return mapToDTO(user);
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .name(user.getName())
                .status(user.getStatus())
                .roleId(user.getRole().getId())
                .roleName(user.getRole().getName())
                .branchId(user.getBranch().getId())
                .branchName(user.getBranch().getName())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .permissions(user.getRole().getPermissions().stream()
                        .map(p -> p.getResource() + ":" + p.getAction())
                        .collect(Collectors.toSet()))
                .build();
    }
}
