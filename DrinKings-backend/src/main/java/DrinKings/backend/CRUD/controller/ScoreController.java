package DrinKings.backend.CRUD.controller;

import DrinKings.backend.CRUD.service.ScoreService;
import DrinKings.backend.CRUD.service.UserService;
import DrinKings.backend.global.dto.NewScore;
import DrinKings.backend.global.exceptions.ResourceNotFoundException;
import DrinKings.backend.global.utils.JwtUtil;
import DrinKings.backend.CRUD.dto.ScoreDto;
import DrinKings.backend.CRUD.entity.Score;
import DrinKings.backend.CRUD.entity.User;
import DrinKings.backend.global.utils.Tuple;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> addScore(
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @RequestBody NewScore score) throws ResourceNotFoundException {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token by removing "Bearer " prefix
            String token = authorizationHeader.substring(7);

            // Extract the username from the token
            String username = JwtUtil.extractUsername(token);

            if (score.getScore() < 0 || score.getScore() > 100) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Score must be between 0 and 100!");
            }

            if (!userService.isUserInLeague(username, score.getLeagueId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("User is not participating in this league!");
            }

            User user = userService.getUserByUsername(username);
            Score existingScore = scoreService.findScoreByDateAndUserIdAndLeagueId(score.getDate(), user.getId(),
                    score.getLeagueId());

            if (existingScore != null) {
                existingScore.setScore(existingScore.getScore() + score.getScore());
                scoreService.updateScore(existingScore);
                return ResponseEntity.ok("Score updated successfully");
            } else {
                ScoreDto scoreDto = ScoreDto.builder()
                        .userId(user.getId())
                        .leagueId(score.getLeagueId())
                        .score(score.getScore())
                        .date(score.getDate())
                        .build();

                scoreService.addScore(scoreDto);
                return ResponseEntity.ok("Score added successfully");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer token provided!");
        }
        // scoreService.addScore(score);
        // return ResponseEntity.ok("Score added successfully");

    }

    @GetMapping("/getAllScoresByLeague/{leagueId}")
    public ResponseEntity<String> getAllScoresByLeague(
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @PathVariable("leagueId") int leagueId) throws ResourceNotFoundException {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token by removing "Bearer " prefix
            String token = authorizationHeader.substring(7);

            // Extract the username from the token
            String username = JwtUtil.extractUsername(token);

            if (!userService.isUserInLeague(username, leagueId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("User is not participating in this league!");
            }

            Iterable<Score> scores = scoreService.getAllScoresByLeague(leagueId);

            List<Score> scoreList = new ArrayList<>();
            scores.forEach(scoreList::add);

            Map<String, List<Tuple<LocalDate, Integer>>> result = new HashMap<>();
            Map<String, Integer> accumulatedScores = new HashMap<>();

            for (Score score : scoreList) {
                String keyUsername = score.getUser().getUsername();

                if (!result.containsKey(keyUsername)) {
                    result.put(keyUsername, new ArrayList<>());
                    accumulatedScores.put(score.getUser().getUsername(), 0);
                }

                int accumulatedScore = accumulatedScores.get(score.getUser().getUsername()) + score.getScore();
                accumulatedScores.put(score.getUser().getUsername(), accumulatedScore);

                result.get(keyUsername).add(new Tuple<>(score.getDate(), accumulatedScore));
            }

            return ResponseEntity.ok(result.toString());

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer token provided!");
        }
    }

    @GetMapping("/getUserScoresByLeague/{leagueId}")
    public ResponseEntity<String> getScoresByLeague(
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @PathVariable("leagueId") int leagueId) throws ResourceNotFoundException {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token by removing "Bearer " prefix
            String token = authorizationHeader.substring(7);

            // Extract the username from the token
            String username = JwtUtil.extractUsername(token);

            if (!userService.isUserInLeague(username, leagueId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("User is not participating in this league!");
            }

            int userId = userService.getUserByUsername(username).getId();
            Iterable<Score> scores = scoreService.getScoresByLeague(leagueId, userId);

            List<Tuple<LocalDate, Integer>> scoreList = new ArrayList<>();
            for (Score score : scores) {
                scoreList.add(new Tuple<LocalDate, Integer>(score.getDate(), score.getScore()));
            }
            return ResponseEntity.ok(scoreList.toString());

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer token provided!");
        }
    }

    @GetMapping("/getTotalScoresByLeague/{leagueId}")
    public ResponseEntity<String> getTotalScoreByLeague(
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @PathVariable("leagueId") int leagueId) throws ResourceNotFoundException {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token by removing "Bearer " prefix
            String token = authorizationHeader.substring(7);

            // Extract the username from the token
            String username = JwtUtil.extractUsername(token);

            if (!userService.isUserInLeague(username, leagueId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("User is not participating in this league!");
            }

            // int userId = userService.getUserByUsername(username).getId();
            // Iterable<Score> scores = scoreService.getScoresByLeague(leagueId, userId);
            // int totalScore = 0;
            // for (Score score : scores) {
            // totalScore += score.getScore();
            // }
            // return ResponseEntity.ok(Integer.toString(totalScore));

            Iterable<Score> scores = scoreService.getAllScoresByLeague(leagueId);

            Map<String, Integer> totalScores = new HashMap<>();

            for (Score score : scores) {
                String keyUsername = score.getUser().getUsername();
                totalScores.put(keyUsername, totalScores.getOrDefault(keyUsername, 0) + score.getScore());
            }

            return ResponseEntity.ok(totalScores.toString());

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer token provided!");
        }
    }

}
