package com.coffecommerce.auth_service.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SellerLoginRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
}
