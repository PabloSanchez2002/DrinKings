package DrinKings.backend.global.dto;

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
    private String date;

    @Builder
    public NewScore(Integer leagueId, Integer score, String date) {
        this.leagueId = leagueId;
        this.score = score;
        this.date = date;
    }

}
