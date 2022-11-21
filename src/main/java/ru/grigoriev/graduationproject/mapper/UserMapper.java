package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.User;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
