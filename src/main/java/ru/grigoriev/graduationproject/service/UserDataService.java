package ru.grigoriev.graduationproject.service;

import org.springframework.stereotype.Service;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.repository.UserDataRepository;

@Service
public class UserDataService {
    private final UserDataRepository repository;
    private final UserMapper userMapper;

    public UserDataService(UserDataRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public UserDto create(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User savedUser = repository.save(user);
        return userMapper.toUserDto(savedUser);
    }
}
