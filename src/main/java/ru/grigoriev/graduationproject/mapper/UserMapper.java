package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
   // @Mapping(target = "registered", dateFormat = "dd-MM-yyyy HH:mm:ss")
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
