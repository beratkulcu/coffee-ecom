package com.coffecommerce.auth_service.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    USER,
    SELLER;

    public String getRole() {
        return this.name();
    }
}
