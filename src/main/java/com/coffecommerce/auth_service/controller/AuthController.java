package com.coffecommerce.auth_service.controller;

import com.coffecommerce.auth_service.constans.ApiResponse;
import com.coffecommerce.auth_service.data.enums.RoleType;
import com.coffecommerce.auth_service.data.request.LoginRequest;
import com.coffecommerce.auth_service.data.request.RefreshTokenRequest;
import com.coffecommerce.auth_service.data.request.RegisterRequest;
import com.coffecommerce.auth_service.data.response.AuthResponse;
import com.coffecommerce.auth_service.service.AuthService;
import com.coffecommerce.auth_service.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.coffecommerce.auth_service.constans.ApiConstans.USER_API;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_API + "/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Boolean>> logout(@RequestParam String refreshToken) {
        refreshTokenService.logout(refreshToken);
        return ResponseEntity.ok(ApiResponse.success(Boolean.TRUE));
    }

    @PostMapping("/seller/register")
    public ResponseEntity<ApiResponse<AuthResponse>> registerSeller(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request, RoleType.SELLER));
    }

}
