package DrinKings.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DrinKings.backend.global.utils.JwtUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Home {

    @GetMapping("/home")
    public String home(@RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token by removing "Bearer " prefix
            String token = authorizationHeader.substring(7);

            // Extract the username from the token
            String name = JwtUtil.extractUsername(token);

            return "Welcome to the home page " + name + "! Your token is: " + token;
        } else {
            return "No Bearer token provided!";
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working";
    }

}
