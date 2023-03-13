package ru.grigoriev.graduationproject.web;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.NestedServletException;
import ru.grigoriev.graduationproject.AbstractControllerTest;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.service.AuthUserService;
import ru.grigoriev.graduationproject.web.constant.Constant;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        User createUser = getNewUser();
        perform(post(path + "/registered")
                .content(mapper().writeValueAsString(createUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(USER_ID)))
                .andExpect(jsonPath("$.name", equalTo(getNewUser().getName())))
                .andExpect(jsonPath("$.email", equalTo(getNewUser().getEmail())))
                .andExpect(status().isOk());

        addMockToken(ADMIN);
        perform(get("/api/v1/users/" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(USER_ID)))
                .andExpect(jsonPath("$.name", equalTo(getNewUser().getName())))
                .andExpect(jsonPath("$.email", equalTo(getNewUser().getEmail())));
    }

    @Test
    void testRegisteredExceptionUnique() {
        User createUser = getNewUserWithDuplicateEmail();
        assertThrows(NestedServletException.class,
                () -> perform(post(path + "/registered")
                        .content(mapper().writeValueAsString(createUser))
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
