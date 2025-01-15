package DrinKings.backend.global.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class NewScore {
    private Integer leagueId;
    private Integer score;
    private LocalDate date;

    @Builder
    public NewScore(Integer leagueId, Integer score, LocalDate date) {
        this.leagueId = leagueId;
        this.score = score;
        this.date = date;
    }

}
