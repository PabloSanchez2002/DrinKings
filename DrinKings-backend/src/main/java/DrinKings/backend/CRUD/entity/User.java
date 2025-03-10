package DrinKings.backend.CRUD.entity;

// import DrinKings.backend.CRUD.entity.League;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import DrinKings.backend.global.utils.Role;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String hashedPassword;
    private String email;
    private boolean activated;

    @ManyToMany(mappedBy = "users")
    @Builder.Default
    private Set<League> leagues = new HashSet<>();

    // Add the roles field as an enum
    @ElementCollection(fetch = FetchType.EAGER) // For multiple roles, use ElementCollection
    @Enumerated(EnumType.STRING) // Store as string values
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
