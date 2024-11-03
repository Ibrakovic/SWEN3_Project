package org.openapitools.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.model.AuthRegisterPostRequest;
import org.openapitools.model.User;
import org.openapitools.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenRegisterUser_thenReturnUserDTO() {
        // Arrange
        AuthRegisterPostRequest request = new AuthRegisterPostRequest("testuser", "test@example.com", "securepassword");
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("securepassword");

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        org.openapitools.dto.UserDTO userDTO = authService.register(request);


        // Assert
        assertEquals("testuser", userDTO.getUsername());
        assertEquals("test@example.com", userDTO.getEmail());
    }
}
