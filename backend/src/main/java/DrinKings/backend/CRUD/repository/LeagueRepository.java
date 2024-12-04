package DrinKings.backend.CRUD.repository;

import DrinKings.backend.CRUD.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    Boolean existsByName(String username);

    Optional<League> findById(Integer id);

    Optional<League> findByName(String username);

}
