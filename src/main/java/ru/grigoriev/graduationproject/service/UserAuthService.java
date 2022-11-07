package ru.grigoriev.graduationproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.model.Role;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.repository.UserDataRepository;

import java.util.Collections;

@Service
@Slf4j
public class UserAuthService {
    private final UserDataRepository repository;
    private final UserMapper userMapper;

    @Autowired
    public UserAuthService(UserDataRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDto registration(UserDto request) {
        User user = userMapper.toUser(request);
        user.setRoles(Collections.singleton(Role.USER));
        User savedUser = repository.save(user);
        return userMapper.toUserDtoWithOutPassword(savedUser);
    }
}
