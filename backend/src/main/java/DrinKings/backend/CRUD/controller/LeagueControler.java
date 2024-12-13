package DrinKings.backend.CRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<League> getLeagueById(@PathVariable("id") int id) {
        return ResponseEntity.ok(leagueService.getLeagueById(id));
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

}
