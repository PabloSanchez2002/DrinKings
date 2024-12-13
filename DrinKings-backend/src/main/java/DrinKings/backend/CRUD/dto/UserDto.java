package DrinKings.backend.CRUD.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;

    @Builder
    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
