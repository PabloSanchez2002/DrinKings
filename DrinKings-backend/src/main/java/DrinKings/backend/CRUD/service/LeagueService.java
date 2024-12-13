package DrinKings.backend.CRUD.service;

import DrinKings.backend.CRUD.dto.LeagueDto;
import DrinKings.backend.CRUD.entity.League;
import DrinKings.backend.CRUD.entity.User;
import DrinKings.backend.CRUD.repository.LeagueRepository;
import DrinKings.backend.CRUD.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private UserRepository userRepository;

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

    public boolean joinLeagueByShareToken(Integer userId, String shareToken) {
        // Step 2: Retrieve the league by shareToken

        System.out.println("Joining league with shareToken: " + shareToken);
        League league = leagueRepository.findByShareToken(shareToken)
                .orElseThrow(() -> new RuntimeException("League not found"));

        // Step 3: Retrieve the user
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Step 4: Check if the user is already part of the league
        if (user.getLeagues().contains(league)) {
            return false; // User is already in the league
        }

        // Step 5: Add the user to the league
        user.getLeagues().add(league);
        league.getUsers().add(user);

        // Step 6: Save the user and league to persist the changes
        userRepository.save(user);
        leagueRepository.save(league);

        return true; // User successfully joined the league
    }

}
