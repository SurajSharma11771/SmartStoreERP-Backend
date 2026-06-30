package com.smartstore.erp.auth.service;

import com.smartstore.erp.auth.dto.LoginRequest;
import com.smartstore.erp.auth.dto.LoginResponse;
import com.smartstore.erp.auth.dto.RegisterRequest;
import com.smartstore.erp.auth.dto.UserSummaryResponse;

public interface AuthService {

    UserSummaryResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}