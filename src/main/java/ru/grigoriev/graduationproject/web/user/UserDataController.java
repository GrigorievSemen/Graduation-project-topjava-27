package ru.grigoriev.graduationproject.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.service.UserDataService;
import ru.grigoriev.graduationproject.web.user.constant.WebConstant;

import javax.validation.Valid;

@RestController
@RequestMapping(value = WebConstant.VERSION_URL + "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDataController {
private final UserDataService service;

    public UserDataController(UserDataService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public UserDto createUserWithBooks(@RequestBody @Valid UserDto request) {
        UserDto response = service.create(request);
        return response;
    }
}
