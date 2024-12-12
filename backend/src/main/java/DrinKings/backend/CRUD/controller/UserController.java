package DrinKings.backend.CRUD.controller;

import DrinKings.backend.CRUD.entity.User;
import DrinKings.backend.CRUD.dto.UserDto;
import DrinKings.backend.CRUD.repository.UserRepository;
import DrinKings.backend.CRUD.service.UserService;
import DrinKings.backend.global.exceptions.AttributeException;
import DrinKings.backend.global.exceptions.ResourceNotFoundException;
import DrinKings.backend.global.dto.LoginResponse;
import DrinKings.backend.global.utils.JwtUtil;

import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
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

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
    // HttpServletResponse response)
    // throws ResourceNotFoundException {
    // // Retrieve the user based on username
    // User user = userService.getUserByUsername(loginRequest.getUsername());
    // System.out.println(user);
    // // Check if the password matches
    // if (BCrypt.checkpw(loginRequest.getPassword(), user.getHashedPassword())) {
    // // Generate JWT token
    // String token = JwtUtil.generateToken(user.getUsername());

    // // Set the token in a cookie
    // Cookie cookie = new Cookie("auth_token", token);
    // cookie.setHttpOnly(true); // Prevents JavaScript access to the token
    // cookie.setSecure(true); // Ensures cookie is sent over HTTPS only
    // cookie.setPath("/"); // The cookie will be available for all paths in the
    // domain
    // cookie.setMaxAge(60 * 60 * 24); // The cookie will expire after one day
    // (adjust as needed)

    // response.addCookie(cookie);

    // // Redirect to home page
    // return ResponseEntity.status(HttpStatus.FOUND)
    // .header(HttpHeaders.LOCATION, "/home")
    // .build();
    // }

    // // Invalid credentials
    // return ResponseEntity.status(401).body("Invalid username or password");
    // }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            HttpServletResponse response)
            throws ResourceNotFoundException {

        // Check if Authorization header is present and uses Basic Auth
        if (authorization == null || !authorization.startsWith("Basic ")) {
            return ResponseEntity.status(400).body("Missing or invalid Authorization header");
        }

        // Decode Base64 encoded username:password
        String base64Credentials = authorization.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

        // Split credentials into username and password
        String[] values = credentials.split(":", 2);
        if (values.length != 2) {
            return ResponseEntity.status(400).body("Invalid Authorization header format");
        }
        String username = values[0];
        String password = values[1];

        // Retrieve the user based on username
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        // Check if the password matches
        if (BCrypt.checkpw(password, user.getHashedPassword())) {
            // Generate JWT token
            String token = JwtUtil.generateToken(user.getUsername());

            // Set the token in a cookie
            Cookie cookie = new Cookie("auth_token", token);
            cookie.setHttpOnly(true); // Prevents JavaScript access to the token
            cookie.setSecure(true); // Ensures cookie is sent over HTTPS only
            cookie.setPath("/"); // The cookie will be available for all paths in the domain
            cookie.setMaxAge(60 * 60 * 24); // The cookie will expire after one day

            response.addCookie(cookie);

            // return ResponseEntity.status(HttpStatus.FOUND)
            // .header(HttpHeaders.LOCATION, "/home")
            // .build();

            return ResponseEntity.ok(new LoginResponse(token));
        }

        // Invalid credentials
        return ResponseEntity.status(401).body("Invalid username or password");
    }

    // @PostMapping("/check-password/{id}")
    // public ResponseEntity<Boolean> checkPassword(@PathVariable("id") int id,
    // @RequestBody String password)
    // throws ResourceNotFoundException {
    // User user = userService.getUserById(id);
    // return ResponseEntity.ok(BCrypt.checkpw(password, user.getHashedPassword()));
    // }

    @PostMapping("/{id}")
    private ResponseEntity<User> update(@PathVariable("id") int id, @RequestBody UserDto userDto)
            throws ResourceNotFoundException, AttributeException {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @PostMapping("/delete/{id}")
    private ResponseEntity<Void> delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<User> getUserById(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    private ResponseEntity<User> getUserByUsername(@PathVariable("username") String username)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/email/{email}")
    private ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

}
