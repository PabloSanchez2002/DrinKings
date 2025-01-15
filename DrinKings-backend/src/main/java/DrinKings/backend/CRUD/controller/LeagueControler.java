package DrinKings.backend.CRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DrinKings.backend.CRUD.dto.LeagueDto;
import DrinKings.backend.CRUD.entity.League;
import DrinKings.backend.CRUD.entity.User;
import DrinKings.backend.CRUD.service.LeagueService;
import DrinKings.backend.CRUD.service.UserService;
import DrinKings.backend.global.dto.JoinLeagueRequest;
import DrinKings.backend.global.exceptions.ResourceNotFoundException;
import DrinKings.backend.global.utils.JwtUtil;

@RestController
@RequestMapping("/league")
public class LeagueControler {

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addLeague(
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @RequestBody LeagueDto leagueDto) throws ResourceNotFoundException {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token by removing "Bearer " prefix
            String token = authorizationHeader.substring(7);

            if (JwtUtil.validateToken(token)) {
                return ResponseEntity.ok(leagueService.addLeague(leagueDto));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid token!");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Missing Bearer token!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLeagueById(
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @PathVariable("id") int id) {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            if (JwtUtil.validateToken(token)) {
                return ResponseEntity.ok(leagueService.getLeagueById(id));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid token!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer token provided!");
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<League> updateLeague(@PathVariable("id") Integer id, @RequestBody LeagueDto leagueDto) {
        // leagueService.updateLeague(id, leagueDto);
        return ResponseEntity.ok(leagueService.updateLeague(id, leagueDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeague(@PathVariable("id") int id) {
        leagueService.deleteLeague(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<League> getLeagueByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(leagueService.getLeagueByName(name));
    }

    @GetMapping("/getByShareToken/{shareToken}")
    public ResponseEntity<League> getLeagueByShareToken(@PathVariable("shareToken") String shareToken) {
        return ResponseEntity.ok(leagueService.getLeagueByShareToken(shareToken));
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinLeague(
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @RequestBody JoinLeagueRequest shareToken) throws ResourceNotFoundException {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token by removing "Bearer " prefix
            String token = authorizationHeader.substring(7);

            // Extract the username from the token
            String username = JwtUtil.extractUsername(token);

            int userId = userService.getUserByUsername(username).getId();
            boolean joined = leagueService.joinLeagueByShareToken(userId, shareToken.getShareToken());
            if (joined) {
                return ResponseEntity.ok("User successfully joined the league.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User is already part of this league.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer token provided!");
        }

    }

}
