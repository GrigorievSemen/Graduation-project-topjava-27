package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // @Mapping(target = "registered", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "password", ignore = true)
    UserDto toUserDtoWithOutPassword(User user);


    User toUser(UserDto userDto);
}
