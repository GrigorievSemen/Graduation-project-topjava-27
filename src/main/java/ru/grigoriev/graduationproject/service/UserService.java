package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.dto.UserUpdateDto;
import ru.grigoriev.graduationproject.model.User;

import java.util.List;

public interface UserService {

    UserDto save(User user);

    UserDto update(UserUpdateDto userUpdateDto);

    List<User> getAll();

    User findByUserName(String name);

    UserDto findBiId(Integer id);

    void delete(Integer id);
}
