package DrinKings.backend.CRUD.repository;

import DrinKings.backend.CRUD.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findById(Integer id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByLeaguesIdAndId(Integer leagueId, Integer userId);

}
