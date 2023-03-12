package ru.grigoriev.graduationproject.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import ru.grigoriev.graduationproject.AbstractControllerTest;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.security.jwt.JwtTokenProvider;
import ru.grigoriev.graduationproject.service.AuthUserService;
import ru.grigoriev.graduationproject.service.UserService;
import ru.grigoriev.graduationproject.web.constant.Constant;
import ru.grigoriev.graduationproject.web.request.AuthenticationRequest;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.grigoriev.graduationproject.UserTestData.*;
import static ru.grigoriev.graduationproject.util.MockSecurity.addMockToken;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

@AutoConfigureWebTestClient
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "test")
public class AuthRestControllerTest extends AbstractControllerTest {
    @Autowired
    private AuthUserService authUserService;
    private final String path = Constant.VERSION_URL + "/auth";

    @Test
    void testLoginReturnsOk() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .name("Admin")
                .password("admin")
                .build();
        perform(post(path + "/login")
                        .content(mapper().writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testLoginException() {
        AuthenticationRequest authenticationRequestException = AuthenticationRequest.builder()
                .name("Admin")
                .password("adminn")
                .build();

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(post(path + "/login")
                        .content(mapper().writeValueAsString(authenticationRequestException))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Invalid name or password"));
    }

    @Test
    void registeredReturnsOk() throws Exception {
        User userCreate = getNewCorrectUser();
        authUserService.create(userCreate);

        addMockToken(userCreate);

        perform(post(path + "/registered")
                        .content(mapper().writeValueAsString(userCreate))
//                        .with(jwt()
//                                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, "Test")))
//                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name", equalTo(userCreate.getName())))
                .andExpect(jsonPath("$.email", equalTo(userCreate.getEmail())))
                .andExpect(status().isOk());

        int id = userCreate.getId();

        perform(get("/api/v1/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(id)))
                .andExpect(jsonPath("$.name", equalTo(getNewCorrectUser().getName())))
                .andExpect(jsonPath("$.email", equalTo(getNewCorrectUser().getEmail())));
    }

    @Test
    void registeredExceptionUnique() {
        assertThrows(DataIntegrityViolationException.class,
                () -> authUserService.create(getNewUserWithDuplicateEmail()));
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(new User("Name", "", "")));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(new User("", "user4@yandex.com", "")));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(new User("", "", "User4")));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(new User("", "user4@yandex.com", "User4")));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(new User("Name", "", "User4")));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(new User("N", "user4@yandex.com", "User4")));
    }
}
