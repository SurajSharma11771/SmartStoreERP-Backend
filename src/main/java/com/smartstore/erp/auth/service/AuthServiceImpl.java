package com.smartstore.erp.auth.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.smartstore.erp.auth.dto.RegisterRequest;
import com.smartstore.erp.auth.dto.UserSummaryResponse;
import com.smartstore.erp.exception.DuplicateResourceException;
import com.smartstore.erp.exception.ResourceNotFoundException;
import com.smartstore.erp.user.entity.User;
import com.smartstore.erp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.smartstore.erp.auth.dto.LoginRequest;
import com.smartstore.erp.auth.dto.LoginResponse;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.smartstore.erp.security.jwt.JwtService;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public UserSummaryResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        return UserSummaryResponse.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new ResourceNotFoundException("Invalid email or password");
    }

    UserSummaryResponse userResponse = UserSummaryResponse.builder()
            .id(user.getId())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .role(user.getRole())
            .build();

    return LoginResponse.builder()
            .accessToken(jwtService.generateToken(user.getEmail()))
            .user(userResponse)
            .build();
}
}