package com.coffecommerce.auth_service.data.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtil {
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

    public boolean isAuthenticatedUser(String username) {
        String currentUser = getCurrentUserName();
        return currentUser != null && currentUser.equals(username); // ✅ doğru kıyas
    }

}
