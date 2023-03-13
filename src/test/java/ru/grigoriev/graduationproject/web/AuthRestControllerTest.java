package ru.grigoriev.graduationproject.web;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import ru.grigoriev.graduationproject.model.Status;
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
        perform(post(path + "/login")
                        .content(mapper().writeValueAsString(AUTHENTICATION_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testLoginException() {
        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(post(path + "/login")
                        .content(mapper().writeValueAsString(AUTHENTICATION_REQUEST_EXS))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Invalid name or password"));
    }

    @Test
    void testRegisteredReturnsOk() throws Exception {

        perform(post(path + "/registered")
                        .content(mapper().writeValueAsString(NEW_CORRECT_USER))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(NEW_CORRECT_USER.getId())))
                .andExpect(jsonPath("$.name", equalTo(NEW_CORRECT_USER.getName())))
                .andExpect(jsonPath("$.email", equalTo(NEW_CORRECT_USER.getEmail())))
                .andExpect(status().isOk());
        addMockToken(NEW_CORRECT_USER);

        perform(get("/api/v1/users/" + NEW_CORRECT_USER.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(NEW_CORRECT_USER.getId())))
                .andExpect(jsonPath("$.name", equalTo(NEW_CORRECT_USER.getName())))
                .andExpect(jsonPath("$.email", equalTo(NEW_CORRECT_USER.getEmail())));
    }

    @Test
    void testRegisteredExceptionUnique() throws Exception {
        addMockToken(ADMIN);
        assertThrows(NestedServletException.class,
                () -> perform(post(path + "/registered")
                        .content(mapper().writeValueAsString(USER_WITH_DUPLICATE_EMAIL))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print()));

    }

    @Test
    void testCreateWithException() {
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(User.builder().name("Name").email("").password("").build()));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(User.builder().name("").email("").password("User4").build()));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(User.builder().name("").email("user4@yandex.com").password("User4").build()));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(User.builder().name("").email("").password("").build()));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(User.builder().name("Name").email("").password("").build()));
        validateRootCause(ConstraintViolationException.class, () -> authUserService.create(User.builder().name("N").email("user4@yandex.com").password("User4").build()));
    }
}