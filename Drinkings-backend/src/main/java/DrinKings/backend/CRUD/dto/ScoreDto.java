package DrinKings.backend.CRUD.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ScoreDto {
    private Integer userId;
    private Integer leagueId;
    private Integer score;
    private String date;

    @Builder
    public ScoreDto(Integer userId, Integer leagueId, Integer score, String date) {
        this.userId = userId;
        this.leagueId = leagueId;
        this.score = score;
        this.date = date;
    }

}