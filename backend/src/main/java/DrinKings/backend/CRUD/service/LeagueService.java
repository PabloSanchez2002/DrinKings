package DrinKings.backend.CRUD.service;

import DrinKings.backend.CRUD.dto.LeagueDto;
import DrinKings.backend.CRUD.entity.League;
import DrinKings.backend.CRUD.repository.LeagueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    public League addLeague(LeagueDto leagueDto) {
        if (leagueRepository.existsByName(leagueDto.getName())) {
            throw new RuntimeException("The league with that name already exists.");
        }

        League league = League.builder()
                .name(leagueDto.getName())
                .description(leagueDto.getDescription())
                .shareToken(null)
                .shareToken(generateShareToken(leagueDto.getName()))
                .build();

    }
}
