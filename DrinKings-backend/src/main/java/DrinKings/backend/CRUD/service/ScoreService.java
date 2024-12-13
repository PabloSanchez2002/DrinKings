package DrinKings.backend.CRUD.service;

import DrinKings.backend.CRUD.entity.League;
import DrinKings.backend.CRUD.entity.Score;
import DrinKings.backend.CRUD.entity.User;
import DrinKings.backend.CRUD.dto.ScoreDto;
import DrinKings.backend.CRUD.repository.LeagueRepository;
import DrinKings.backend.CRUD.repository.ScoreRepository;
import DrinKings.backend.CRUD.repository.UserRepository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LeagueRepository leagueRepository;

    public Score addScore(ScoreDto scoreDto) {

        User user = userRepository.findById(scoreDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        League league = leagueRepository.findById(scoreDto.getLeagueId())
                .orElseThrow(() -> new RuntimeException("League not found"));

        boolean isInLeague = userRepository.existsByLeaguesIdAndId(scoreDto.getLeagueId(), scoreDto.getUserId());
        if (!isInLeague) {
            throw new RuntimeException("User is not in the league");
        }

        Score score = Score.builder()
                .user(user)
                .score(scoreDto.getScore())
                .league(league)
                .date(LocalDate.now())
                .build();
        return scoreRepository.save(score);
    }

    public Iterable<Score> getScoresByLeague(int leagueId, int userId) {
        return scoreRepository.findByLeagueIdAndUserId(leagueId, userId);
    }

}
