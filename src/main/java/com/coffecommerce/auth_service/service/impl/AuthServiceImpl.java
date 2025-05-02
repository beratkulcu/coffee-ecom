package com.coffecommerce.auth_service.service.impl;

import com.coffecommerce.auth_service.constans.ApiResponse;
import com.coffecommerce.auth_service.data.entity.Seller;
import com.coffecommerce.auth_service.data.enums.RefreshTokenStatus;
import com.coffecommerce.auth_service.data.request.*;
import com.coffecommerce.auth_service.data.response.AuthResponse;
import com.coffecommerce.auth_service.data.entity.RefreshToken;
import com.coffecommerce.auth_service.data.entity.Role;
import com.coffecommerce.auth_service.data.entity.User;
import com.coffecommerce.auth_service.data.enums.RoleType;
import com.coffecommerce.auth_service.exception.AppException;
import com.coffecommerce.auth_service.repository.RefreshTokenRepository;
import com.coffecommerce.auth_service.repository.RoleRepository;
import com.coffecommerce.auth_service.repository.SellerRepository;
import com.coffecommerce.auth_service.repository.UserRepository;
import com.coffecommerce.auth_service.security.JwtTokenProvider;
import com.coffecommerce.auth_service.service.AuthService;
import com.coffecommerce.auth_service.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.coffecommerce.auth_service.data.enums.ActivityStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SellerRepository sellerRepository;


    @Override
    public ApiResponse<AuthResponse> register(RegisterRequest request) {
        userRepository.findByUsername(request.username())
                .ifPresent(user -> {
                    throw new AppException("Username is already taken");
                });

        userRepository.findByEmail(request.email())
                .ifPresent(email -> {
                    throw new AppException("Email is already registered");
                });

        Role userRole = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new AppException("User role not found"));

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phoneNumber())
                .userActivityStatus(ACTIVE)
                .isEnabled(true)
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);

        Set<String> roles = Set.of(userRole.getName().name());
        String accessToken = jwtTokenProvider.generateAccessToken(user.getUsername(), roles);

        String refreshToken = refreshTokenService.generateRefreshToken(user).getToken();

        return ApiResponse.success(new AuthResponse(accessToken, refreshToken));
    }

    @Override
    public ApiResponse<AuthResponse> login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AppException("Invalid password");
        }

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        String accessToken = jwtTokenProvider.generateAccessToken(user.getUsername(), roles);

        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .filter(existingToken -> existingToken.getStatus() == RefreshTokenStatus.ACTIVE
                        && !refreshTokenService.isTokenExpired(existingToken))
                .orElseGet(() -> {
                    refreshTokenRepository.findByUser(user).ifPresent(refreshTokenService::isTokenExpired);
                    return refreshTokenService.generateRefreshToken(user);
                });


        return ApiResponse.success(new AuthResponse(accessToken, refreshToken.getToken()));
    }

    @Override
    public ApiResponse<AuthResponse> refreshToken(RefreshTokenRequest request) {
        RefreshToken token = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new AppException("Refresh token not found"));

        if (token.getStatus() != RefreshTokenStatus.ACTIVE) {
            throw new AppException("Invalid refresh token");
        }

        if (refreshTokenService.isTokenExpired(token)) {
            refreshTokenService.isTokenExpired(token);
            throw new AppException("Refresh token is expired");
        }

        User user = token.getUser();
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getUsername(), roles);
        return ApiResponse.success(new AuthResponse(newAccessToken, token.getToken()));
    }

    @Override
    public ApiResponse<AuthResponse> register(RegisterRequest request, RoleType roleType) {
        userRepository.findByUsername(request.username())
                .ifPresent(user -> {
                    throw new AppException("Username is already taken");
                });

        userRepository.findByEmail(request.email())
                .ifPresent(email -> {
                    throw new AppException("Email is already registered");
                });

        Role role = roleRepository.findByName(roleType)
                .orElseThrow(() -> new AppException("Role not found"));

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phoneNumber())
                .userActivityStatus(ACTIVE)
                .isEnabled(true)
                .roles(Set.of(role))
                .build();
        userRepository.save(user);

        Set<String> roles = Set.of(role.getName().name());
        String accessToken = jwtTokenProvider.generateAccessToken(user.getUsername(), roles);
        String refreshToken = refreshTokenService.generateRefreshToken(user).getToken();

        return ApiResponse.success(new AuthResponse(accessToken, refreshToken));
    }

    @Override
    public ApiResponse<AuthResponse> registerSeller(SellerRegisterRequest request) {
        if (sellerRepository.existsByEmail(request.email())) {
            throw new AppException("Email already in use");
        }

        Seller seller = Seller.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .storeName(request.storeName())
                .storeDescription(request.storeDescription())
                .sellerActivityStatus(ACTIVE)
                .build();

        sellerRepository.save(seller);

        String accessToken = jwtTokenProvider.generateAccessToken(seller.getEmail(), Set.of(RoleType.SELLER.name()));
        String refreshToken = refreshTokenService.generateRefreshTokenSeller(seller).getToken();

        return ApiResponse.success(new AuthResponse(accessToken, refreshToken));
    }

    @Override
    public ApiResponse<AuthResponse> loginSeller(SellerLoginRequest request) {
        Seller seller = sellerRepository.findByEmail(request.email())
                .orElseThrow(() -> new AppException("Seller not found"));

        if (!passwordEncoder.matches(request.password(), seller.getPassword())) {
            throw new AppException("Invalid password");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(seller.getEmail(), Set.of(RoleType.SELLER.name()));
        String refreshToken = jwtTokenProvider.generateRefreshToken(seller.getEmail(), Set.of(RoleType.SELLER.name()));

        return ApiResponse.success(new AuthResponse(accessToken, refreshToken));
    }
}
