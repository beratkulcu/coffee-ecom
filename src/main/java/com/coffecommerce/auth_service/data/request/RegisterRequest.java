package com.coffecommerce.auth_service.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.coffecommerce.Validations.*;

public record RegisterRequest(
        @NotBlank(message = FIRST_NAME_REQUIRED)
        String firstName,

        @NotBlank(message = LAST_NAME_REQUIRED)
        String lastName,

        @NotBlank(message = USERNAME_REQUIRED)
        String username,

        @NotBlank(message = EMAIL_REQUIRED)
        @Email(message = EMAIL_INVALID)
        String email,

        @NotBlank(message = PASSWORD_REQUIRED)
        String password,

        @Pattern(regexp = "^(\\+90|0)?5\\d{9}$", message = PHONE_INVALID)
        String phoneNumber
){}
