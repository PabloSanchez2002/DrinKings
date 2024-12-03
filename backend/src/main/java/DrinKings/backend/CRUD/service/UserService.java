package DrinKings.backend.CRUD.service;

import DrinKings.backend.CRUD.entity.User;
import DrinKings.backend.CRUD.repository.UserRepository;
import DrinKings.backend.global.exceptions.AttributeException;
import DrinKings.backend.global.exceptions.ResourceNotFoundException;
import DrinKings.backend.CRUD.dto.UserDto;
import DrinKings.backend.global.utils.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    public User getUserByUsername(String username) throws ResourceNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username + "."));
    }

    public User getUserByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email + "."));
    }

    public User addUser(UserDto dto) throws AttributeException {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new AttributeException("The user with that username already exists.");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new AttributeException("The user with that email already exists.");
        }

        User user = User.builder()
                .username(dto.getUsername())
                // .hashedPassword(dto.getPassword()) // save hashed password
                .hashedPassword(PasswordUtil.hashPassword(dto.getPassword())) // save hashed password
                .email(dto.getEmail())
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    public User updateUser(int id, UserDto dto) throws ResourceNotFoundException, AttributeException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        ;
        /// If the username of the user is different from the one you want to change and
        /// there is already a user with that id
        // if (userRepository.existsByUsername(dto.getUsername())
        // && userRepository.findByUsername(dto.getUsername()).get().getId() != id) {
        // throw new AttributeException("The user with that username already exists.");
        // }
        user.setUsername(dto.getUsername());
        // user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(int id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        userRepository.delete(user);
    }

    private int autoIncrementId() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return 1;
        }
        // return users.get(users.size() - 1).getId() + 1;
        return users.stream().max(Comparator.comparing(User::getId)).get().getId() + 1;
    }

}
