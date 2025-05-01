package com.coffecommerce;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validations {
    public static final String FIRST_NAME_REQUIRED = "First name is required.";
    public static final String LAST_NAME_REQUIRED = "Last name is required.";
    public static final String USERNAME_REQUIRED = "Username is required.";
    public static final String EMAIL_REQUIRED = "Email is required.";
    public static final String EMAIL_INVALID = "Invalid email format.";
    public static final String PASSWORD_REQUIRED = "Password is required.";
    public static final String PHONE_INVALID = "Phone number must be in the format +905XXXXXXXXX or 05XXXXXXXXX.";
}
