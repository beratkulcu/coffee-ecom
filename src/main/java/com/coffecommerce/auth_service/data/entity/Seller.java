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

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sellers")
public class Seller extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_generator")
    @SequenceGenerator(name = "seller_generator", sequenceName = "seller_seq", initialValue = 100000)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits and can start with '+'")
    private String phoneNumber;

    @Column(nullable = false)
    private String storeName;

    @Enumerated(EnumType.STRING)
    private ActivityStatus sellerActivityStatus;

    @Column(columnDefinition = "TEXT")
    private String storeDescription;

    public boolean isEnabled() {
        return ACTIVE == this.sellerActivityStatus;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "seller_roles",
            joinColumns = @JoinColumn(name = "seller_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }
}
