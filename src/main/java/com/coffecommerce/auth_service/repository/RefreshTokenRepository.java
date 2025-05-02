package com.coffecommerce.auth_service.repository;

import com.coffecommerce.auth_service.data.entity.Seller;
import com.coffecommerce.auth_service.data.enums.RefreshTokenStatus;
import com.coffecommerce.auth_service.data.entity.RefreshToken;
import com.coffecommerce.auth_service.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    List<RefreshToken> findAllByUserAndStatus(User user, RefreshTokenStatus status);

    List<RefreshToken> findAllByStatus(RefreshTokenStatus refreshTokenStatus);

    List<RefreshToken> findAllBySellerAndStatus(Seller seller, RefreshTokenStatus status);

    Optional<RefreshToken> findBySeller(Seller seller);
}
