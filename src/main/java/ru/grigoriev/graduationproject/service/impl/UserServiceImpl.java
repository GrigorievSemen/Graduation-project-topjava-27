package ru.grigoriev.graduationproject.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.model.Role;
import ru.grigoriev.graduationproject.model.Status;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.repository.UserRepository;
import ru.grigoriev.graduationproject.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public UserDto register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setStatus(Status.ACTIVE);

        User registerUser = repository.save(user);
        log.info("IN register -> user: {} successfully registered",registerUser);
        return userMapper.toUserDto(registerUser);
    }

    @Override
    public List<User> getAll() {
        List<User> result = repository.findAll();
        log.info("IN getAll -> {} users found",result.size());
        return result;
    }

    @Override
    public User findByUserName(String name) {
        Optional<User> result = Optional.ofNullable(repository.findByName(name)
                .orElseThrow( () ->
                        new NotFoundException("User does not exist in the database")));

        log.info("IN findByUserName -> user: {} found by username: {}",result,name);
        return result.get();
    }

    @Override
    public UserDto findBiId(Integer id) {
        Optional<User> user = Optional.ofNullable(repository.findById(id)
                .orElseThrow( () ->
                    new NotFoundException("User does not exist in the database")));
        UserDto result = userMapper.toUserDto(user.get());
        log.info("IN findBiId -> user: {} found by id: {}",user,id);
        return result;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
        log.info("In delete -> user with id: {} successfully deleted",id);
    }
}
