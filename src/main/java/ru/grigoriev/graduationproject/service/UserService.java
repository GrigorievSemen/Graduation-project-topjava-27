package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.web.user.request.delete.UserDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.UserUpdateRequest;

import java.util.List;

public interface UserService {

    UserDto update(UserUpdateRequest userUpdateRequest);

    List<UserDto> getAll();

    User findByUserName(String name);

    UserDto findBiId(Integer id);

    void delete(UserDeleteRequest userDeleteRequest);
}
