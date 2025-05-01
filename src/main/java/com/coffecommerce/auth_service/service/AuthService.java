package com.coffecommerce.auth_service.service;

import com.coffecommerce.auth_service.constans.ApiResponse;
import com.coffecommerce.auth_service.data.enums.RoleType;
import com.coffecommerce.auth_service.data.request.LoginRequest;
import com.coffecommerce.auth_service.data.request.RefreshTokenRequest;
import com.coffecommerce.auth_service.data.request.RegisterRequest;
import com.coffecommerce.auth_service.data.response.AuthResponse;

public interface AuthService {
    ApiResponse<AuthResponse> register(RegisterRequest request);

    ApiResponse<AuthResponse> login(LoginRequest request);

    ApiResponse<AuthResponse> refreshToken(RefreshTokenRequest request);

    ApiResponse<AuthResponse> register(RegisterRequest request, RoleType roleType);
}
