package DrinKings.backend.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import DrinKings.backend.CRUD.entity.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

}
