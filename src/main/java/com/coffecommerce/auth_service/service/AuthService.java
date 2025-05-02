package com.coffecommerce.auth_service.service;

import com.coffecommerce.auth_service.constans.ApiResponse;
import com.coffecommerce.auth_service.data.enums.RoleType;
import com.coffecommerce.auth_service.data.request.*;
import com.coffecommerce.auth_service.data.response.AuthResponse;
import jakarta.validation.Valid;

public interface AuthService {
    ApiResponse<AuthResponse> register(RegisterRequest request);

    ApiResponse<AuthResponse> login(LoginRequest request);

    ApiResponse<AuthResponse> refreshToken(RefreshTokenRequest request);

    ApiResponse<AuthResponse> register(RegisterRequest request, RoleType roleType);

    ApiResponse<AuthResponse> registerSeller(@Valid SellerRegisterRequest request);

    ApiResponse<AuthResponse> loginSeller(SellerLoginRequest request);
}
