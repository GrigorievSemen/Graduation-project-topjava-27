package ru.grigoriev.graduationproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.repository.UserRepository;
import ru.grigoriev.graduationproject.security.jwt.JwtUser;
import ru.grigoriev.graduationproject.service.UserService;
import ru.grigoriev.graduationproject.util.UserUtil;
import ru.grigoriev.graduationproject.web.request.delete.UserDeleteRequest;
import ru.grigoriev.graduationproject.web.request.update.UserUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final int FLAG_FOR_CHECK_BY_ID = 0;
    private final int FLAG_FOR_CHECK_BY_NAME = 1;
    private final int FLAG_FOR_CHECK_ADMIN_OR_NO = 2;

    @Transactional
    @Override
    public UserDto update(UserUpdateRequest userUpdateRequest) {
        checkPossibilities(userUpdateRequest.getOld_name(), FLAG_FOR_CHECK_BY_NAME);
        getAuthentication(userUpdateRequest.getOld_name(), userUpdateRequest.getOld_password());

        User result = getUserByName(userUpdateRequest.getOld_name());

        result.setEmail(userUpdateRequest.getEmail());
        result.setName(userUpdateRequest.getNew_name());
        result.setPassword(userUpdateRequest.getNew_password());
        result.setUpdated_at(LocalDateTime.now());
        UserUtil.setPasswordWithEncoder(result);

        User updateUser = repository.save(result);
        log.info("IN update -> user: {} successfully updated", updateUser);
        return userMapper.toUserDto(updateUser);
    }

    @Override
    public List<UserDto> getAll() {
        checkPossibilities(null, FLAG_FOR_CHECK_ADMIN_OR_NO);
        List<User> result = repository.findAll();
        log.info("IN getAll -> {} users found", result.size());
        return userMapper.toDtoList(result);
    }

    @Override
    public User findByUserName(String name) {
        checkPossibilities(name, FLAG_FOR_CHECK_BY_NAME);
        User result = getUserByName(name);
        log.info("IN findByUserName -> user: {} found by username: {}", result, name);
        return result;
    }

    @Override
    public UserDto findBiId(Integer id) {
        checkPossibilities(id, FLAG_FOR_CHECK_BY_ID);
        Optional<User> user = Optional.ofNullable(repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("User does not exist in the database")));
        UserDto result = userMapper.toUserDto(user.get());
        log.info("IN findBiId -> user: {} found by id: {}", user, id);
        return result;
    }

    @Transactional
    @Override
    public void delete(UserDeleteRequest userDeleteRequest) {
        checkPossibilities(userDeleteRequest.getId(), FLAG_FOR_CHECK_BY_ID);
        getAuthentication(userDeleteRequest.getName(), userDeleteRequest.getPassword());
        repository.deleteById(userDeleteRequest.getId());
        log.info("In delete -> user with name: {} successfully deleted", userDeleteRequest.getName());
    }

    private User getUserByName(String name) {
        Optional<User> result = Optional.ofNullable(repository.findByName(name)
                .orElseThrow(() ->
                        new NotFoundException("User does not exist in the database")));
        return result.get();
    }

    private void getAuthentication(String principal, String credentials) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, credentials));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid name or password");
        }
    }

    public void checkPossibilities(Object object, int flag) {
        JwtUser userAuth = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean adminOrNo = userAuth.getAuthorities().stream()
                .map(v -> v.getAuthority().equals("ROLE_ADMIN"))
                .findFirst()
                .orElse(false);

        switch (flag) {
            case FLAG_FOR_CHECK_BY_ID -> {
                int idUser = (int) object;
                if (!adminOrNo) {
                    if (userAuth.getId() != idUser) {
                        log.info("The user with id {} tried to get another ( {} ) user's data", userAuth.getId(), idUser);
                        throw new NotFoundException("Users cannot receive, change data of other users");
                    }
                }
            }
            case FLAG_FOR_CHECK_BY_NAME -> {
                String nameUser = (String) object;
                if (!adminOrNo) {
                    if (!userAuth.getName().equals(nameUser)) {
                        log.info("The user with name {} tried to get another ( {} ) user's data", userAuth.getId(), nameUser);
                        throw new NotFoundException("Users cannot receive, change data of other users");
                    }
                }
            }
            case FLAG_FOR_CHECK_ADMIN_OR_NO -> {
                if (!adminOrNo) {
                    log.info("The user with id {} tried to get all user's dates", userAuth.getId());
                    throw new NotFoundException("Users cannot receive, change data of other users");
                }
            }
        }
    }
}
