package ru.grigoriev.graduationproject.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.NestedServletException;
import ru.grigoriev.graduationproject.AbstractControllerTest;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.service.AuthUserService;
import ru.grigoriev.graduationproject.web.constant.Constant;
import ru.grigoriev.graduationproject.web.request.AuthenticationRequest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

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
public class DishRestControllerTest extends AbstractControllerTest {
    private final String path = Constant.VERSION_URL + "/admin/dish";

    @Test
    void testCreateDishReturnsOk() throws Exception {
        addMockToken(ADMIN);

        perform(post(path + "/save")
                .content(mapper().writeValueAsString(getDISH()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(DISH.getId())))
                .andExpect(jsonPath("$.name", equalTo(DISH.getName())));
    }
    @Test
    void testCreateExceptionUnique() throws Exception {
        addMockToken(ADMIN);
                perform(post(path + "/save")
                        .content(mapper().writeValueAsString(DUPLICATE_DISH))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


//    @Test
//    void testGetUserByIdReturnsOk() throws Exception {
//        addMockToken(getUser());
//
//        perform(get(path + "/" + getUser().getId()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(getUser().getId())))
//                .andExpect(jsonPath("$.name", equalTo(getUser().getName())))
//                .andExpect(jsonPath("$.email", equalTo(getUser().getEmail())))
//                .andExpect(jsonPath("$.roles[0]", equalTo(new ArrayList<>(getUser().getRoles()).get(0).toString())));
//    }
//
//    @Test
//    void testGetUserByNameReturnsOk() throws Exception {
//        addMockToken(getUser());
//
//        perform(get(path + "/" + "?name=" + getUser().getName()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(getUser().getId())))
//                .andExpect(jsonPath("$.name", equalTo(getUser().getName())))
//                .andExpect(jsonPath("$.email", equalTo(getUser().getEmail())))
//                .andExpect(jsonPath("$.roles[0]", equalTo(new ArrayList<>(getUser().getRoles()).get(0).toString())));
//    }
//
//    @Test
//    void testGetUserNotFound() {
//        addMockToken(getAdmin());
//
//        NestedServletException exception = assertThrows(NestedServletException.class, () ->
//                perform(get(path + "/" + USER_ID_NOT_FOUND))
//                        .andDo(print()));
//
//        assertThat(exception.getCause().getMessage(), equalTo("User does not exist in the database"));
//    }
//
//    @Test
//    void testTryDataUsersOtherUser() {
//        addMockToken(getUser());
//
//        NestedServletException exception = assertThrows(NestedServletException.class, () ->
//                perform(get(path + "/" + USER_ID_NOT_FOUND))
//                        .andDo(print()));
//
//        assertThat(exception.getCause().getMessage(), equalTo("Users cannot receive, change data of other users"));
//    }
//
//    @Test
//    void testGetAllUserReturnsOk() throws Exception {
//        addMockToken(getAdmin());
//
////        USER_DTO_MATCHER.assertMatch(service.getAll(),getAllUserDto());
//
//        perform(get(path + "/all" ))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(USER_DTO_MATCHER.contentJson((getAllUserDto())));
//    }
//
//    @Test
//    void testUpdateUserReturnsOk() throws Exception {
//        addMockToken(getUser());
//
//        perform(post(path + "/update" )
//                .content(mapper().writeValueAsString(getUserUpdateRequest()))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(getUser().getId())))
//                .andExpect(jsonPath("$.name", equalTo(getUserUpdateRequest().getNew_name())))
//                .andExpect(jsonPath("$.email", equalTo(getUserUpdateRequest().getEmail())));
//    }
//
//    @Test
//    void testDeleteUserReturnsOk() throws Exception {
//        addMockToken(getUser());
//
//        perform(post(path + "/delete" )
//                .content(mapper().writeValueAsString(getUserDeleteRequest()))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//        NestedServletException exception = assertThrows(NestedServletException.class, () ->
//                perform(get(path + "/" + getUser().getId()))
//                        .andDo(print()));
//
//        assertThat(exception.getCause().getMessage(), equalTo("User does not exist in the database"));
//    }





}
