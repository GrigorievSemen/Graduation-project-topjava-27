package ru.grigoriev.graduationproject.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.grigoriev.graduationproject.security.jwt.JwtUser;
import ru.grigoriev.graduationproject.web.user.constant.Constant;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/admin",
        produces = MediaType.APPLICATION_JSON_VALUE)

public class ErrorController {
    @PostMapping("/403")
    public String error403() {
        JwtUser userAuth = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.warn("User with id {} tried to access the admin service.", userAuth.getId());
        return "You do not have permission to access this service.";
    }
}
