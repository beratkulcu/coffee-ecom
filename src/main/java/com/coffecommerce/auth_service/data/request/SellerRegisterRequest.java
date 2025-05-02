package com.coffecommerce.auth_service.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SellerRegisterRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotBlank
        @Pattern(regexp = "^\\+?[0-9]{10,15}$",
                message = "Phone number must be between 10 to 15 digits and can start with '+'")
        String phoneNumber,

        @NotBlank
        String storeName,

        String storeDescription
) {
}
