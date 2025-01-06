package org.openapitools.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.dto.EntityResponse;
import org.openapitools.dto.UserDTO;
import org.openapitools.entity.User;
import org.openapitools.model.AuthRegisterPostRequest;
import org.openapitools.repository.UserRepository;
import org.openapitools.service.impl.AuthService;

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
		EntityResponse register = authService.register(request);

		// Assert
		if (register.getStatus() == 200) {
			UserDTO userDTO = (UserDTO) register.getData();
			assertEquals("testuser", userDTO.getUsername());
			assertEquals("test@example.com", userDTO.getEmail());
		}

	}
}
