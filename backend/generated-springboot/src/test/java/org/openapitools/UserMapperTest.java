package org.openapitools;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.openapitools.mapper.UserMapper;
import org.openapitools.model.User;
import org.openapitools.model.UserDto;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testToEntity() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");

        User user = userMapper.toEntity(userDto);

        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getEmail(), user.getEmail());
    }

    @Test
    public void testToDto() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        UserDto userDto = userMapper.toDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
    }
}
