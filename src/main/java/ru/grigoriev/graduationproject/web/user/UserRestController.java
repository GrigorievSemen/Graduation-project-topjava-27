package ru.grigoriev.graduationproject.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.dto.UserUpdateDto;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.model.Status;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.repository.UserRepository;
import ru.grigoriev.graduationproject.service.UserService;
import ru.grigoriev.graduationproject.web.user.constant.Constant;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    private final UserService service;
    private final UserMapper userMapper;

    public UserRestController(UserService userService, UserMapper userMapper) {
        this.service = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") int id) {
        log.info("IN getUserById");
        UserDto result = service.findBiId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<UserDto> getUserByName(@RequestParam("name") String name) {
        log.info("IN getUserByUserName");
        User user = service.findByUserName(name);
        UserDto result = userMapper.toUserDto(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        log.info("IN update");
        UserDto result = service.update(userUpdateDto);
        return ResponseEntity.ok(result);
    }
}
