package com.coffecommerce.auth_service.data.job;

import com.coffecommerce.auth_service.data.enums.RefreshTokenStatus;
import com.coffecommerce.auth_service.data.entity.RefreshToken;
import com.coffecommerce.auth_service.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenCleanupJob {
    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void expireOldTokens() {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByStatus(RefreshTokenStatus.ACTIVE);
        LocalDateTime now = LocalDateTime.now();
        tokens.stream()
                .filter(token -> token.getExpiryDate().isBefore(now))
                .forEach(token -> {
                    token.setStatus(RefreshTokenStatus.EXPIRED);
                    refreshTokenRepository.save(token);
                    log.info("Token expired and updated: {}", token.getId());
                });
    }
}
