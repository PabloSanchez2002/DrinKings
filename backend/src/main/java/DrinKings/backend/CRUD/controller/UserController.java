package DrinKings.backend.CRUD.controller;

import DrinKings.backend.CRUD.entity.User;
import DrinKings.backend.CRUD.repository.UserRepository;
import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@RestController
public class UserController {

    private UserRepository userRepository;

    @PostMapping("/register")
    private ResponseEntity<User> register(@RequestBody String username, @RequestBody String password,
            @RequestBody String email) {
        // User existingUser = userRepository.findByUsername(username);
        // if (existingUser != null) {
        // return ResponseEntity.badRequest().build();
        // }
        // existingUser = userRepository.findByEmail(email);
        // if (existingUser != null) {
        // return ResponseEntity.badRequest().build();
        // }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setHashedPassword(password);
        user.setActivated(false);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
