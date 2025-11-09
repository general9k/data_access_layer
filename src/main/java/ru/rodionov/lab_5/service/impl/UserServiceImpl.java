package ru.rodionov.lab_5.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rodionov.lab_5.exception.DuplicateEmailException;
import ru.rodionov.lab_5.exception.UserNotFoundException;
import ru.rodionov.lab_5.model.User;
import ru.rodionov.lab_5.repository.UserRepository;
import ru.rodionov.lab_5.service.UserService;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User registerUser(String name, String email) {
        validateEmail(email);

        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateEmailException("User with email " + email + " already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    private void validateEmail(String email) {
        if (email == null || !Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}