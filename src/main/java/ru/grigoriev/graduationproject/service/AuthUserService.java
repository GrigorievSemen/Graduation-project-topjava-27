package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.User;

public interface AuthUserService {
    UserDto create(User user);

    User findByUserName(String name);
}
