package DrinKings.backend.CRUD.controller;

import DrinKings.backend.CRUD.service.ScoreService;
import DrinKings.backend.CRUD.service.UserService;
import DrinKings.backend.global.exceptions.ResourceNotFoundException;
import DrinKings.backend.global.utils.JwtUtil;
import DrinKings.backend.CRUD.dto.ScoreDto;
import DrinKings.backend.CRUD.entity.Score;
import DrinKings.backend.CRUD.entity.User;
import DrinKings.backend.global.utils.Tuple;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<String> addScore(@RequestBody ScoreDto score) {
        scoreService.addScore(score);
        return ResponseEntity.ok("Score added successfully");
    }

    @GetMapping("/getScoresByLeague/{leagueId}")
    public ResponseEntity<String> getScoresByLeague(
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @PathVariable("leagueId") int leagueId) throws ResourceNotFoundException {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token by removing "Bearer " prefix
            String token = authorizationHeader.substring(7);

            // Extract the username from the token
            String username = JwtUtil.extractUsername(token);

            int userId = userService.getUserByUsername(username).getId();
            Iterable<Score> scores = scoreService.getScoresByLeague(leagueId, userId);

            List<Tuple<Date, Integer>> scoreList = new ArrayList<>();
            for (Score score : scores) {
                scoreList.add(new Tuple<Date, Integer>(Date.valueOf(score.getDate()), score.getScore()));
            }
            return ResponseEntity.ok(scoreList.toString());

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer token provided!");
        }
    }

}
