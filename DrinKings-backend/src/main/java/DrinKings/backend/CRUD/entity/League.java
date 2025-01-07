package DrinKings.backend.CRUD.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LEAGUES")
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    @Column(unique = true, nullable = false)
    private String shareToken;

    @ManyToMany
    @JoinTable(name = "user_league", joinColumns = @JoinColumn(name = "league_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "league")
    @Builder.Default
    private Set<Score> scores = new HashSet<>();

}
