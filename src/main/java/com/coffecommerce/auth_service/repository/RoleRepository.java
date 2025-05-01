package com.coffecommerce.auth_service.repository;

import com.coffecommerce.auth_service.entity.Role;
import com.coffecommerce.auth_service.data.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);

    boolean existsByName(RoleType name);
}
