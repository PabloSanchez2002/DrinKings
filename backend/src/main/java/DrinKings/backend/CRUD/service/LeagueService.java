package DrinKings.backend.CRUD.service;

import DrinKings.backend.CRUD.dto.LeagueDto;
import DrinKings.backend.CRUD.entity.League;
import DrinKings.backend.CRUD.repository.LeagueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    public League addLeague(LeagueDto leagueDto) {

        Random random = new Random();

        if (leagueRepository.existsByName(leagueDto.getName())) {
            throw new RuntimeException("The league with that name already exists.");
        }

        League league = League.builder()
                .name(leagueDto.getName())
                .description(leagueDto.getDescription())
                .shareToken(null)
                .shareToken(random.nextInt(100) + leagueDto.getName())
                .build();

        return leagueRepository.save(league);
    }

    public League getLeagueById(Integer id) {
        return leagueRepository.findById(id).orElseThrow(() -> new RuntimeException("League not found"));
    }

    public League getLeagueByName(String name) {
        return leagueRepository.findByName(name).orElseThrow(() -> new RuntimeException("League not found"));
    }

    public League getLeagueByShareToken(String shareToken) {
        return leagueRepository.findByShareToken(shareToken)
                .orElseThrow(() -> new RuntimeException("League not found"));
    }

    public void deleteLeague(Integer id) {
        leagueRepository.deleteById(id);
    }

    public League updateLeague(Integer id, LeagueDto leagueDto) {
        League league = getLeagueById(id);
        league.setName(leagueDto.getName());
        league.setDescription(leagueDto.getDescription());
        return leagueRepository.save(league);
    }

}
