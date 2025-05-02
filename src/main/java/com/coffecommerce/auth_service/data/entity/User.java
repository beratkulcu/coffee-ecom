package com.coffecommerce.auth_service.data.entity;

import com.coffecommerce.auth_service.data.entity.abstracts.AbstractEntity;
import com.coffecommerce.auth_service.data.enums.ActivityStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static com.coffecommerce.auth_service.data.enums.ActivityStatus.ACTIVE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_seq", initialValue = 100000)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    @Pattern(regexp = "^(\\+90|0)?5\\d{9}$", message = "Phone number must be in the format +905XXXXXXXXX or 05XXXXXXXXX")
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private ActivityStatus userActivityStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column
    private boolean isEnabled;

    public boolean isEnabled() {
        return ACTIVE == this.userActivityStatus;
    }

    @Override
    public Long getId() {
        return id;
    }
}
