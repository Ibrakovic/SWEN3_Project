package org.openapitools.service.impl;

import org.openapitools.constant.Constants;
import org.openapitools.dto.EntityResponse;
import org.openapitools.dto.UserDTO;
import org.openapitools.entity.User;
import org.openapitools.mapper.UserMapper;
import org.openapitools.model.AuthRegisterPostRequest;
import org.openapitools.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	private final UserRepository userRepository;

	@Autowired
	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public EntityResponse register(AuthRegisterPostRequest request) {
		logger.info("Registration request received: {}", request.toString());

		Long count = userRepository.countByEmail(request.getEmail());
		if (count != null && count > 0) {
			return new EntityResponse(Constants.BAD, "User registration failed \n Email already exist", null);
		}

		count = userRepository.countByUsername(request.getUsername());
		if (count != null && count > 0) {
			return new EntityResponse(Constants.BAD, "User registration failed \n Username already exist", null);
		}

		UserDTO userDTO = new UserDTO();
		userDTO.requestToUser(request);
		try {
			User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
			logger.info("User created: {}", user.toString());
			User savedUser = userRepository.save(user);
			logger.info("User saved: {}", savedUser);
			return new EntityResponse(Constants.OK, "User registration successfully", userDTO);
		} catch (Exception e) {
			e.printStackTrace(); // Zeige den vollständigen Stacktrace im Log an
			throw new RuntimeException("User registration failed: " + e.getMessage()); // Fehler explizit werfen
		}
	}

	public UserDTO login(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user != null && user.getPassword().equals(password)) {
			return UserMapper.INSTANCE.userToUserDTO(user);
		}
		return null;
	}

}