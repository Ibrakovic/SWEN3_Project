package org.openapitools.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.User;
import org.openapitools.model.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Mapping von DTO zu Entität
    User toEntity(UserDto userDto);

    // Mapping von Entität zu DTO
    UserDto toDto(User user);
}
