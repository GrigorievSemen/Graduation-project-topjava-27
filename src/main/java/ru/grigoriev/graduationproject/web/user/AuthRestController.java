package ru.grigoriev.graduationproject.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.AuthenticationRequestDto;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.security.jwt.JwtTokenProvider;
import ru.grigoriev.graduationproject.service.UserService;
import ru.grigoriev.graduationproject.web.user.constant.Constant;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/auth",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody AuthenticationRequestDto requestDto) {
        log.info("IN login");
        try {
            String name = requestDto.getName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, requestDto.getPassword()));
            User user = userService.findByUserName(name);

            if (user == null) {
                throw new UsernameNotFoundException("User with name: " + name + " not found");
            }

            String token = jwtTokenProvider.createToken(name,new ArrayList<>(user.getRoles()));

            Map<Object, Object> response = new HashMap<>();
            response.put("name", name);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            throw new BadCredentialsException("Invalid name or password");
        }
    }

    @PostMapping("/registered")
    public ResponseEntity<UserDto> registered(@RequestBody @Valid User user) {
        log.info("IN registered");
        UserDto result = userService.register(user);
        return ResponseEntity.ok(result);
    }
}
