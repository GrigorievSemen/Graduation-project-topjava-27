package ru.grigoriev.graduationproject.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.service.UserService;
import ru.grigoriev.graduationproject.web.user.constant.Constant;
import ru.grigoriev.graduationproject.web.user.request.delete.UserDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.UserUpdateRequest;

import javax.validation.Valid;
import java.util.List;

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
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/")
    public ResponseEntity<UserDto> getUserByName(@RequestParam(value = "name", required = false) String name) {
        log.info("IN getUserByUserName");
        User user = service.findByUserName(name);
        UserDto result = userMapper.toUserDto(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDto>> getAll() {
        log.info("IN getAll");
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping(value = "/update")
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        log.info("IN update");
        UserDto result = service.update(userUpdateRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestBody @Valid UserDeleteRequest userDeleteRequest) {
        log.info("IN delete");
        service.delete(userDeleteRequest);
        return ResponseEntity.ok("User " + userDeleteRequest.getName() + " successfully deleted.");
    }
}
