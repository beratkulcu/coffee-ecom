package com.coffecommerce.auth_service.controller;

import com.coffecommerce.auth_service.constans.ApiResponse;
import com.coffecommerce.auth_service.data.request.SellerLoginRequest;
import com.coffecommerce.auth_service.data.request.SellerRegisterRequest;
import com.coffecommerce.auth_service.data.response.AuthResponse;
import com.coffecommerce.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.coffecommerce.auth_service.constans.ApiConstans.SELLER_API;

@RestController
@RequestMapping(SELLER_API + "/auth")
@RequiredArgsConstructor
public class SellerAuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody SellerRegisterRequest request) {
        return ResponseEntity.ok(authService.registerSeller(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody SellerLoginRequest request) {
        return ResponseEntity.ok(authService.loginSeller(request));
    }
}
