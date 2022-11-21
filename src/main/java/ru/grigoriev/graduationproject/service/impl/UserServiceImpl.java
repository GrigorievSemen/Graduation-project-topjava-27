package ru.grigoriev.graduationproject.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.dto.UserUpdateDto;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.model.Role;
import ru.grigoriev.graduationproject.model.Status;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.repository.UserRepository;
import ru.grigoriev.graduationproject.service.UserService;
import ru.grigoriev.graduationproject.util.UserUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, UserMapper userMapper, UserUtil userUtil, AuthenticationManager authenticationManager1) {
        this.repository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Override
    public UserDto save(User user) {

        UserUtil.setPassword(user);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setStatus(Status.ACTIVE);

        User registerUser = repository.save(user);
        log.info("IN register -> user: {} successfully registered",registerUser);
        return userMapper.toUserDto(registerUser);
    }

    @Override
    public UserDto update(UserUpdateDto userUpdateDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userUpdateDto.getOld_name(),userUpdateDto.getOld_password()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid name or password");
        }

        Optional<User> result = Optional.ofNullable(repository.findByName(userUpdateDto.getOld_name())
                .orElseThrow( () ->
                        new NotFoundException("User does not exist in the database")));

        result.get().setEmail(userUpdateDto.getEmail());
        result.get().setName(userUpdateDto.getNew_name());
        UserUtil.setPassword(result.get());
        result.get().setUpdated_at(new Date());
        User updateUser = repository.save(result.get());
        log.info("IN update -> user: {} successfully updated",updateUser);
        return userMapper.toUserDto(updateUser);
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
