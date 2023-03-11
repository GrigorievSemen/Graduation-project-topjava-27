package ru.grigoriev.graduationproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.model.Role;
import ru.grigoriev.graduationproject.model.Status;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.repository.UserRepository;
import ru.grigoriev.graduationproject.service.AuthUserService;
import ru.grigoriev.graduationproject.util.UserUtil;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthUserServiceImpl implements AuthUserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto create(User user) {
        UserUtil.setPasswordWithEncoder(user);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setStatus(Status.ACTIVE);

        User registerUser = repository.save(user);
        log.info("IN create -> user: {} successfully registered", registerUser);
        return userMapper.toUserDto(registerUser);
    }

    @Override
    public User findByUserName(String name) {
        User result = getUserByName(name);
        log.info("IN findByUserName -> user: {} found by username: {}", result, name);
        return result;
    }

    private User getUserByName(String name) {
        Optional<User> result = Optional.ofNullable(repository.findByName(name)
                .orElseThrow(() ->
                        new NotFoundException("User does not exist in the database")));
        return result.get();
    }
}
