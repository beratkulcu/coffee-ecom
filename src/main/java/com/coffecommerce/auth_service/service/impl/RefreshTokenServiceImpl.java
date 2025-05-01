package com.coffecommerce.auth_service.service.impl;

import com.coffecommerce.auth_service.data.enums.RefreshTokenStatus;
import com.coffecommerce.auth_service.data.util.SecurityUtil;
import com.coffecommerce.auth_service.entity.RefreshToken;
import com.coffecommerce.auth_service.entity.User;
import com.coffecommerce.auth_service.exception.AppException;
import com.coffecommerce.auth_service.repository.RefreshTokenRepository;
import com.coffecommerce.auth_service.security.JwtTokenProvider;
import com.coffecommerce.auth_service.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public RefreshToken generateRefreshToken(User user) {
        invalidateAllActiveTokens(user);

        String token = jwtTokenProvider.generateRefreshToken(user.getUsername(), getUserRoles(user));
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plus(Duration.ofMillis(jwtTokenProvider.getRefreshTokenExpiration())))
                .status(RefreshTokenStatus.ACTIVE)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    private Set<String> getUserRoles(User user) {
        return user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isTokenExpired(RefreshToken token) {
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            token.setStatus(RefreshTokenStatus.EXPIRED);
            refreshTokenRepository.save(token);
            return true;
        }
        return false;
    }


    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException("Refresh token not found"));
    }

    @Override
    public void logout(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException("Refresh token not found"));

        if (refreshToken.getStatus() == RefreshTokenStatus.EXPIRED) {
            throw new AppException("Refresh token is already expired");
        }

        if (refreshToken.getStatus() == RefreshTokenStatus.LOGOUT) {
            throw new AppException("Refresh token is already logged out");
        }

        if (!SecurityUtil.isAuthenticatedUser(refreshToken.getUser().getUsername())){
            throw new AppException("User is not authenticated");
        }

        refreshToken.setStatus(RefreshTokenStatus.LOGOUT);
        refreshTokenRepository.save(refreshToken);
    }

    private void invalidateAllActiveTokens(User user) {
        refreshTokenRepository.findAllByUserAndStatus(user, RefreshTokenStatus.ACTIVE)
                .forEach(token -> {
                    token.setStatus(RefreshTokenStatus.EXPIRED);
                    refreshTokenRepository.save(token);
                });
    }
}
