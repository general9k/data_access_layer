package ru.rodionov.lab_5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rodionov.lab_5.exception.DuplicateEmailException;
import ru.rodionov.lab_5.exception.UserNotFoundException;
import ru.rodionov.lab_5.model.User;
import ru.rodionov.lab_5.repository.UserRepository;
import ru.rodionov.lab_5.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("Тест на регистрацию нового пользователя")
    void testRegisterNewUser() {
        String name = "Иван";
        String email = "ivan@example.com";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName(name);
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        User result = userService.registerUser(name, email);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Тест на регистрацию нового пользователя с существующим email в БД")
    void testRegisterUserDuplicateEmail() {
        String name = "Иван";
        String email = "ivan@example.com";
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Existing User");
        existingUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        DuplicateEmailException exception = assertThrows(
                DuplicateEmailException.class,
                () -> userService.registerUser(name, email)
        );

        assertTrue(exception.getMessage().contains("already exists"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Тест на получение пользователя по email")
    void testFindUserByEmail() {
        String email = "ivan@example.com";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("Иван");
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        User result = userService.findUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    @DisplayName("Тест на неполучение пользователя по email")
    void testFindUserByEmailNotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.findUserByEmail(email)
        );

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    @DisplayName("Тест на получение списка пользователей")
    void testListAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Иван");
        user1.setEmail("ivan@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Мария");
        user2.setEmail("maria@example.com");

        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> result = userService.listAllUsers();

        assertEquals(2, result.size());
        assertEquals(expectedUsers, result);
    }
}