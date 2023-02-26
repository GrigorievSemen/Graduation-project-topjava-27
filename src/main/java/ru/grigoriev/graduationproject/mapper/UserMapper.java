package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    List<UserDto> toDtoList(List<User> userList);

    User toUser(UserDto userDto);
}
