package DrinKings.backend.CRUD.dto;

import DrinKings.backend.CRUD.entity.League;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor
public class LeagueDto {
    private String name;
    private String description;

    // public League toEntity() {
    // return League.builder()
    // .name(name)
    // .description(description)
    // .build();
    // }

    @Builder
    public LeagueDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
