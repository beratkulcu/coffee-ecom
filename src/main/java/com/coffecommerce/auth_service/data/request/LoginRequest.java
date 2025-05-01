package com.coffecommerce.auth_service.data.request;

import jakarta.validation.constraints.NotBlank;

import static com.coffecommerce.Validations.PASSWORD_REQUIRED;
import static com.coffecommerce.Validations.USERNAME_REQUIRED;

public record LoginRequest(
        @NotBlank(message = USERNAME_REQUIRED)
        String username,

        @NotBlank(message = PASSWORD_REQUIRED)
        String password
) {
}
