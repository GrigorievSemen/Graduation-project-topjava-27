package ru.grigoriev.graduationproject.web;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.NestedServletException;
import ru.grigoriev.graduationproject.AbstractControllerTest;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.service.AuthUserService;
import ru.grigoriev.graduationproject.service.UserService;
import ru.grigoriev.graduationproject.web.constant.Constant;
import ru.grigoriev.graduationproject.web.request.AuthenticationRequest;

import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
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
public class UserRestControllerTest extends AbstractControllerTest {
    @Autowired
    private AuthUserService authUserService;
//    @Autowired
//    private UserService service;
    private final String path = Constant.VERSION_URL + "/users";

    @Test
    void testGetUserByIdReturnsOk() throws Exception {
        addMockToken(getUser());

        perform(get(path + "/" + getUser().getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(getUser().getId())))
                .andExpect(jsonPath("$.name", equalTo(getUser().getName())))
                .andExpect(jsonPath("$.email", equalTo(getUser().getEmail())))
                .andExpect(jsonPath("$.roles[0]", equalTo(new ArrayList<>(getUser().getRoles()).get(0).toString())));
    }

    @Test
    void testGetUserByNameReturnsOk() throws Exception {
        addMockToken(getUser());

        perform(get(path + "/" + "?name=" + getUser().getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(getUser().getId())))
                .andExpect(jsonPath("$.name", equalTo(getUser().getName())))
                .andExpect(jsonPath("$.email", equalTo(getUser().getEmail())))
                .andExpect(jsonPath("$.roles[0]", equalTo(new ArrayList<>(getUser().getRoles()).get(0).toString())));
    }

    @Test
    void testGetUserNotFound() {
        addMockToken(getAdmin());

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + USER_ID_NOT_FOUND))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("User does not exist in the database"));
    }

    @Test
    void testTryDataUsersOtherUser() {
        addMockToken(getUser());

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + USER_ID_NOT_FOUND))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Users cannot receive, change data of other users"));
    }

    @Test
    void testGetAllUserReturnsOk() throws Exception {
        addMockToken(getAdmin());

//        USER_DTO_MATCHER.assertMatch(service.getAll(),getAllUserDto());

        perform(get(path + "/all" ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(USER_DTO_MATCHER.contentJson((getAllUserDto())));
    }

    @Test
    void testUpdateUserReturnsOk() throws Exception {
        addMockToken(getUser());

        perform(post(path + "/update" )
                .content(mapper().writeValueAsString(getUserUpdateRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(getUser().getId())))
                .andExpect(jsonPath("$.name", equalTo(getUserUpdateRequest().getNew_name())))
                .andExpect(jsonPath("$.email", equalTo(getUserUpdateRequest().getEmail())));
    }

    @Test
    void testDeleteUserReturnsOk() throws Exception {
        addMockToken(getUser());

        perform(post(path + "/delete" )
                .content(mapper().writeValueAsString(getUserDeleteRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + getUser().getId()))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("User does not exist in the database"));
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
                        .contentType(MediaType.APPLICATION_JSON)));
        assertThat(exception.getCause().getMessage(), equalTo("Invalid name or password"));
    }

    @Test
    void registeredReturnsOk() throws Exception {
        User userCreate = getNewCorrectUser();
        authUserService.create(userCreate);

        addMockToken(USER_FOR_TOKEN);

        perform(post(path + "/registered")
                        .content(mapper().writeValueAsString(userCreate))
                        .with(jwt()
                                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, "Test")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("User4")))
                .andExpect(jsonPath("$.email", equalTo("user4@yandex.com")))
                .andExpect(status().isOk());

        int id = userCreate.getId();

        perform(get("/api/v1/users/" + id)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, "Test")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(id)))
                .andExpect(jsonPath("$.name", equalTo(getNewCorrectUser().getName())))
                .andExpect(jsonPath("$.email", equalTo(getNewCorrectUser().getEmail())));
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
