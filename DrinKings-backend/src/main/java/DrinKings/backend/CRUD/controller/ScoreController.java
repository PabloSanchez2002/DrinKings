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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
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
    public ResponseEntity<Map<String, Object>> getAllScoresByLeague(
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @PathVariable("leagueId") int leagueId) throws ResourceNotFoundException {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No Bearer token provided!"));
        }

        String token = authorizationHeader.substring(7);
        String username = JwtUtil.extractUsername(token);

        if (!userService.isUserInLeague(username, leagueId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "User is not participating in this league!"));
        }

        Iterable<Score> scores = scoreService.getAllScoresByLeague(leagueId);
        List<Score> scoreList = new ArrayList<>();
        scores.forEach(scoreList::add);

        if (scoreList.isEmpty()) {
            return ResponseEntity.ok(Map.of("labels", List.of(), "datasets", List.of()));
        }

        // Step 1: Sort scores by date
        scoreList.sort(Comparator.comparing(Score::getDate));

        // Step 2: Generate a continuous list of dates from the first to the last score
        LocalDate startDate = scoreList.get(0).getDate();
        LocalDate endDate = scoreList.get(scoreList.size() - 1).getDate();
        List<LocalDate> allDates = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            allDates.add(date);
        }

        // Step 3: Organize user scores
        Map<String, List<Tuple<LocalDate, Integer>>> userScores = new HashMap<>();
        for (Score score : scoreList) {
            userScores.computeIfAbsent(score.getUser().getUsername(), k -> new ArrayList<>())
                    .add(new Tuple<>(score.getDate(), score.getScore()));
        }

        // Step 4: Generate accumulated scores for each user
        Map<String, List<Integer>> datasets = new HashMap<>();
        for (String user : userScores.keySet()) {
            List<Tuple<LocalDate, Integer>> rawScores = userScores.get(user);
            List<Integer> accumulatedData = new ArrayList<>();
            int lastScore = 0;

            for (LocalDate date : allDates) {
                // If there is a new score for this date, accumulate it
                for (Tuple<LocalDate, Integer> entry : rawScores) {
                    if (entry.getFirst().equals(date)) {
                        lastScore += entry.getSecond();
                        break;
                    }
                }
                accumulatedData.add(lastScore);
            }

            datasets.put(user, accumulatedData);
        }

        // Step 5: Predefined color palette
        List<String> colors = List.of(
                "#42A5F5", "#66BB6A", "#FFA726", "#AB47BC", "#FF7043", "#26C6DA",
                "#7E57C2", "#D4E157", "#FFCA28", "#EC407A", "#29B6F6", "#8D6E63",
                "#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5",
                "#26A69A", "#66BB6A", "#9CCC65", "#D4E157", "#FFEE58", "#FFCA28",
                "#FFA726", "#FF7043", "#8D6E63", "#BDBDBD", "#78909C");

        // Step 6: Build final JSON response for Chart.js
        List<Map<String, Object>> datasetList = new ArrayList<>();
        int colorIndex = 0;

        for (String user : datasets.keySet()) {
            datasetList.add(Map.of(
                    "label", user,
                    "data", datasets.get(user),
                    "borderColor", colors.get(colorIndex % colors.size()), // Cycle through colors
                    "fill", false));
            colorIndex++;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", allDates.stream().map(LocalDate::toString).toList());
        response.put("datasets", datasetList);

        return ResponseEntity.ok(response);
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
    public ResponseEntity<?> getTotalScoreByLeague(
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

            return ResponseEntity.ok(totalScores);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer token provided!");
        }
    }

}
