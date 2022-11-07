package ru.grigoriev.graduationproject.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.service.UserAuthService;
import ru.grigoriev.graduationproject.web.user.constant.Constant;

import javax.validation.Valid;

@RestController
@RequestMapping(value = Constant.VERSION_URL + "/auth",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
private final UserAuthService service;

    public AuthController(UserAuthService service) {
        this.service = service;
    }

    @PostMapping(value = "/registration")
    public UserDto registration(@RequestBody @Valid UserDto request) {
        UserDto response = service.registration(request);
        return response;
    }
}
