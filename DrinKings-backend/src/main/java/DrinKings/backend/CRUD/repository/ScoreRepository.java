package DrinKings.backend.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import DrinKings.backend.CRUD.entity.Score;

import java.time.LocalDate;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

    Iterable<Score> findByLeagueIdAndUserId(int leagueId, int userId);

    Score findByDateAndUserIdAndLeagueId(LocalDate date, Integer userId, Integer leagueId);

}
