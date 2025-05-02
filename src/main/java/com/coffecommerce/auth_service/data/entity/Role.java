package com.coffecommerce.auth_service.data.entity;

import com.coffecommerce.auth_service.data.entity.abstracts.AbstractEntity;
import com.coffecommerce.auth_service.data.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    @SequenceGenerator(name = "role_generator", sequenceName = "role_seq", initialValue = 100000)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType name;

    @Override
    public Long getId() {
        return this.id;
    }
}
