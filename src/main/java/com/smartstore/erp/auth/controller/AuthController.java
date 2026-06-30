package com.smartstore.erp.auth.controller;

import com.smartstore.erp.auth.dto.LoginRequest;
import com.smartstore.erp.auth.dto.LoginResponse;
import com.smartstore.erp.auth.dto.RegisterRequest;
import com.smartstore.erp.auth.dto.UserSummaryResponse;
import com.smartstore.erp.auth.service.AuthService;
import com.smartstore.erp.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

        @PostMapping("/register")
        public ApiResponse<UserSummaryResponse> register(@Valid @RequestBody RegisterRequest request) {
            UserSummaryResponse response = authService.register(request);
            return ApiResponse.success("User registered successfully", response);
        }

    @PostMapping("/login")
public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

    LoginResponse response = authService.login(request);

    return ApiResponse.success("Login successful", response);
}
}