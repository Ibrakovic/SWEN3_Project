package org.openapitools.mapper;

import javax.annotation.processing.Generated;
import org.openapitools.dto.UserDTO;
import org.openapitools.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-31T18:33:35+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( userDTO.getPassword() );
        user.setEmail( userDTO.getEmail() );
        user.setUsername( userDTO.getUsername() );

        return user;
    }

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername( user.getUsername() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPassword( user.getPassword() );

        return userDTO;
    }
}
