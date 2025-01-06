package org.openapitools.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.openapitools.dto.UserDTO;
import org.openapitools.entity.User;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testUserDTOToUserMapping() {
        // Create a UserDTO instance
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("testuser@example.com");
        userDTO.setPassword("securepassword");

        // Map UserDTO to User
        User user = userMapper.userDTOToUser(userDTO);

        // Verify the mapping
        assertNotNull(user);
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPassword(), user.getPassword());
    }

    @Test
    public void testUserToUserDTOMapping() {
        // Create a User instance
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("securepassword");

        // Map User to UserDTO
        UserDTO userDTO = userMapper.userToUserDTO(user);

        // Verify the mapping
        assertNotNull(userDTO);
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }
}
