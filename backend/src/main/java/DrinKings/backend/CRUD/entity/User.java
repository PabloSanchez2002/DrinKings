package DrinKings.backend.CRUD.entity;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

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

    // @ManyToMany
    // @JoinTable(name = "user_league", joinColumns = @JoinColumn(name = "user_id"),
    // inverseJoinColumns = @JoinColumn(name = "league_id"))
    // private Set<League> leagues;

    // Getters and Setters
}
