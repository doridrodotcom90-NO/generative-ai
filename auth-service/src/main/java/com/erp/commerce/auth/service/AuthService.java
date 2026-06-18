package com.erp.commerce.auth.service;

import com.erp.commerce.auth.dto.LoginRequest;
import com.erp.commerce.auth.dto.LoginResponse;
import com.erp.commerce.common.exception.BusinessException;
import com.erp.commerce.common.exception.ResourceNotFoundException;
import com.erp.commerce.auth.entity.User;
import com.erp.commerce.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        log.info("Login attempt for: {}", request.getEmailOrPhone());

        User user = userRepository.findByEmailOrPhone(request.getEmailOrPhone(), request.getEmailOrPhone())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getDeletedAt() != null) {
            throw new BusinessException("User account has been deleted");
        }

        if (user.getAccountLocked()) {
            throw new BusinessException("User account is locked");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= 5) {
                user.setAccountLocked(true);
                userRepository.save(user);
                throw new BusinessException("Account locked due to multiple failed login attempts");
            }
            userRepository.save(user);
            throw new BusinessException("Invalid credentials");
        }

        user.setFailedLoginAttempts(0);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getExpirationTime())
                .user(buildUserResponse(user))
                .build();
    }

    public User getUserById(Long id) {
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private LoginResponse.UserDTO buildUserResponse(User user) {
        return LoginResponse.UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .name(user.getName())
                .role(user.getRole().getCode())
                .branchId(user.getBranch().getId())
                .branchName(user.getBranch().getName())
                .permissions(user.getRole().getPermissions().stream()
                        .map(p -> p.getResource() + ":" + p.getAction())
                        .collect(Collectors.toSet()))
                .build();
    }
}
