package com.coffecommerce.auth_service.config;

import com.coffecommerce.auth_service.entity.Role;
import com.coffecommerce.auth_service.data.enums.RoleType;
import com.coffecommerce.auth_service.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleSeeder {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void seedRoles() {
        for (RoleType roleType : RoleType.values()) {
            boolean exists = roleRepository.existsByName(roleType);
            if (!exists) {
                Role role = Role.builder()
                        .name(roleType)
                        .build();
                roleRepository.save(role);
                log.info("Role {} created", roleType.name());
            }else {
                log.info("Role {} already exists", roleType.name());
            }
        }
    }
}
