package DrinKings.backend.CRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DrinKings.backend.CRUD.dto.LeagueDto;
import DrinKings.backend.CRUD.entity.League;
import DrinKings.backend.CRUD.service.LeagueService;

@RestController
@RequestMapping("/league")
public class LeagueControler {

    @Autowired
    private LeagueService leagueService;

    @PostMapping
    public ResponseEntity<League> addLeague(@RequestBody LeagueDto leagueDto) {
        return ResponseEntity.ok(leagueService.addLeague(leagueDto));
    }

}
