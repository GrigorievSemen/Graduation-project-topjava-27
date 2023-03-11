package ru.grigoriev.graduationproject.web;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.security.jwt.JwtTokenProvider;
import ru.grigoriev.graduationproject.service.AuthUserService;
import ru.grigoriev.graduationproject.web.constant.Constant;
import ru.grigoriev.graduationproject.web.request.AuthenticationRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/auth",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserService userService;

    @PostMapping("/login")
    @Operation(summary = "Login user.")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody AuthenticationRequest requestDto) {
        log.info("IN login");
        String name = requestDto.getName();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, requestDto.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid name or password");
        }

        User user = userService.findByUserName(name);

        String token = jwtTokenProvider.createToken(name, new ArrayList<>(user.getRoles()));

        Map<Object, Object> response = new HashMap<>();
        response.put("name", name);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping("/registered")
    public ResponseEntity<UserDto> registered(@RequestBody @Valid User user) {
        log.info("IN registered");
        UserDto result = userService.create(user);
        return ResponseEntity.ok(result);
    }
}
