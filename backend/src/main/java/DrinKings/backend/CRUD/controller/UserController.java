package DrinKings.backend.CRUD.controller;

import DrinKings.backend.CRUD.entity.User;
import DrinKings.backend.CRUD.dto.UserDto;
import DrinKings.backend.CRUD.repository.UserRepository;
import DrinKings.backend.CRUD.service.UserService;
import DrinKings.backend.global.exceptions.AttributeException;
import DrinKings.backend.global.exceptions.ResourceNotFoundException;
import DrinKings.backend.global.dto.LoginRequest;
import DrinKings.backend.global.dto.LoginResponse;
import DrinKings.backend.global.utils.JwtUtil;

import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private ResponseEntity<User> register(@RequestBody UserDto userDto) throws AttributeException {
        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
            throws ResourceNotFoundException {
        // Retrieve the user based on username
        User user = userService.getUserByUsername(loginRequest.getUsername());

        // Check if the password matches
        if (BCrypt.checkpw(loginRequest.getPassword(), user.getHashedPassword())) {
            // Generate JWT token
            String token = JwtUtil.generateToken(user.getUsername());

            // Set the token in a cookie
            Cookie cookie = new Cookie("auth_token", token);
            cookie.setHttpOnly(true); // Prevents JavaScript access to the token
            cookie.setSecure(true); // Ensures cookie is sent over HTTPS only
            cookie.setPath("/"); // The cookie will be available for all paths in the domain
            cookie.setMaxAge(60 * 60 * 24); // The cookie will expire after one day (adjust as needed)

            response.addCookie(cookie);

            // Redirect to home page
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/home")
                    .build();
        }

        // Invalid credentials
        return ResponseEntity.status(401).body("Invalid username or password");
    }

    @PostMapping("/{id}")
    private ResponseEntity<User> update(@PathVariable("id") int id, @RequestBody UserDto userDto)
            throws ResourceNotFoundException, AttributeException {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

}
