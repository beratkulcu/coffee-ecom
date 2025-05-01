package com.coffecommerce.auth_service.service;

import com.coffecommerce.auth_service.entity.RefreshToken;
import com.coffecommerce.auth_service.entity.User;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken(User user);

    boolean isTokenExpired(RefreshToken refreshToken);

    RefreshToken findByToken(String token);

    void logout(String token);
}
