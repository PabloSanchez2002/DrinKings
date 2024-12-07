package DrinKings.backend.CRUD.dto;

import DrinKings.backend.CRUD.entity.User;
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

    // public User toEntity() {
    // return User.builder()
    // .name(username)
    // .email(email)
    // .password(password)
    // .build();
    // }

    @Builder
    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
