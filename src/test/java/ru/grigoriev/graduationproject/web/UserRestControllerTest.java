package ru.grigoriev.graduationproject.web;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.NestedServletException;
import ru.grigoriev.graduationproject.AbstractControllerTest;
import ru.grigoriev.graduationproject.web.constant.Constant;

import java.util.ArrayList;

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
public class UserRestControllerTest extends AbstractControllerTest {
    private final String path = Constant.VERSION_URL + "/users";

    @Test
    void testGetUserByIdReturnsOk() throws Exception {
        addMockToken(USER);

        perform(get(path + "/" + USER.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(USER.getId())))
                .andExpect(jsonPath("$.name", equalTo(USER.getName())))
                .andExpect(jsonPath("$.email", equalTo(USER.getEmail())))
                .andExpect(jsonPath("$.roles[0]", equalTo(new ArrayList<>(USER.getRoles()).get(0).toString())));
    }

    @Test
    void testGetUserByNameReturnsOk() throws Exception {
        addMockToken(USER);

        perform(get(path + "/" + "?name=" + USER.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(USER.getId())))
                .andExpect(jsonPath("$.name", equalTo(USER.getName())))
                .andExpect(jsonPath("$.email", equalTo(USER.getEmail())))
                .andExpect(jsonPath("$.roles[0]", equalTo(new ArrayList<>(USER.getRoles()).get(0).toString())));
    }

    @Test
    void testGetUserNotFound() {
        addMockToken(ADMIN);

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + USER_ID_NOT_FOUND))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("User does not exist in the database"));
    }

    @Test
    void testTryDataUsersOtherUser() {
        addMockToken(USER);

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + USER_ID_NOT_FOUND))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Users cannot receive, change data of other users"));
    }

    @Test
    void testGetAllUserReturnsOk() throws Exception {
        addMockToken(ADMIN);

        perform(get(path + "/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(USER_DTO_MATCHER.contentJson(ALL_USER_DTO));
    }

    @Test
    void testUpdateUserReturnsOk() throws Exception {
        addMockToken(USER);

        perform(post(path + "/update")
                .content(mapper().writeValueAsString(USER_UPDATE_REQUEST))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(USER.getId())))
                .andExpect(jsonPath("$.name", equalTo(USER_UPDATE_REQUEST.getNew_name())))
                .andExpect(jsonPath("$.email", equalTo(USER_UPDATE_REQUEST.getEmail())));
    }

    @Test
    void testDeleteUserReturnsOk() throws Exception {
        addMockToken(USER);

        perform(post(path + "/delete")
                .content(mapper().writeValueAsString(USER_DELETE_REQUEST))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + USER.getId()))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("User does not exist in the database"));
    }
}
