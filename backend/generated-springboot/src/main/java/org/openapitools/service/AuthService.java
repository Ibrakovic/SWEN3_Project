package org.openapitools.service;

import org.openapitools.dto.UserDTO;
import org.openapitools.mapper.UserMapper;
import org.openapitools.model.AuthRegisterPostRequest;
import org.openapitools.model.User;
import org.openapitools.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO register(AuthRegisterPostRequest request) {
        logger.info("Registration request received: " + request.toString());
        UserDTO userDTO = new UserDTO();
        userDTO.requestToUser(request);
        try {
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            logger.info("User created: {}", user.toString());
            User savedUser = userRepository.save(user);
            logger.info("User saved: {}", savedUser);
            return UserMapper.INSTANCE.userToUserDTO(savedUser);
        } catch (Exception e) {
            e.printStackTrace(); // Zeige den vollständigen Stacktrace im Log an
            throw new RuntimeException("User registration failed: " + e.getMessage()); // Fehler explizit werfen
        }
    }


    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && !Objects.equals(password, user.getPassword())) {
            return UserMapper.INSTANCE.userToUserDTO(user);
        }
        return null;
    }


}