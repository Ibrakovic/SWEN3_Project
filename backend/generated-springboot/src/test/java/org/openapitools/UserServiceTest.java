package org.openapitools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.model.User;
import org.openapitools.repository.UserRepository;
import org.openapitools.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("testuser", "testuser@example.com", "password");
        user.setId(1L);
    }

    @Test
    void testFindUserByEmail() {
        // Arrange
        String email = "testuser@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.findUserByEmail(email);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindUserByEmail_NotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<User> foundUser = userService.findUserByEmail(email);

        // Assert
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    // Weitere Tests für createUser, getUserById, updateUser etc. können hier hinzugefügt werden
}
