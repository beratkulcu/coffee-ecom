package com.coffecommerce.auth_service.service;

import com.coffecommerce.auth_service.data.entity.RefreshToken;
import com.coffecommerce.auth_service.data.entity.Seller;
import com.coffecommerce.auth_service.data.entity.User;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken(User user);

    boolean isTokenExpired(RefreshToken refreshToken);

    RefreshToken findByToken(String token);

    void logout(String token);

    RefreshToken generateRefreshTokenSeller(Seller seller);
}
