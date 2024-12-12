package DrinKings.backend.global.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordRequest {
    private String username;
    private String currentPassword;
    private String newPassword;

}
