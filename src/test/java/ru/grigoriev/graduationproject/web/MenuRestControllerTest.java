package ru.grigoriev.graduationproject.web;

import org.junit.jupiter.api.BeforeEach;
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
import ru.grigoriev.graduationproject.service.VoteService;
import ru.grigoriev.graduationproject.web.constant.Constant;
import ru.grigoriev.graduationproject.web.request.menu.MenuCreateRequest;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.grigoriev.graduationproject.DataTest.*;
import static ru.grigoriev.graduationproject.util.MockSecurity.addMockToken;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

@AutoConfigureWebTestClient
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "test")
public class MenuRestControllerTest extends AbstractControllerTest {
    private final String path = Constant.VERSION_URL + "/admin/menu/";
    @Autowired
    private VoteService voteService;

    @BeforeEach
    public void addToken() {
        addMockToken(ADMIN);
    }

    @Test
    void testCreateMenuReturnsOk() throws Exception {
        MenuCreateRequest createMenu = getNewMenuCreateRequest();

        perform(post(path + "save")
                .content(mapper().writeValueAsString(List.of(createMenu)))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        perform(get(path + NEW_MENU_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(NEW_MENU_ID)))
                .andExpect(MENU_DTO_MATCHER.contentJson(MENU_DTO));
    }

    @Test
    void testGetMenuByIdReturnsOk() throws Exception {
        perform(get(path + RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(MENU_ID)))
                .andExpect(MENU_DTO_MATCHER.contentJson(MENU_DTO));
    }

    @Test
    void testGetMenuByRestaurantIdReturnsOk() throws Exception {
        System.err.println(LocalDate.now());
        perform(get(path + "restaurant" + MENU_REQUEST_PARAMS_BY_RESTAURANT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MENU_DTO_MATCHER.contentJson(MENU_DTO_BY_RESTAURANT));
    }

    @Test
    void testGetMenuAllReturnsOk() throws Exception {
        perform(get(path + "all" + MENU_REQUEST_PARAMS_ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MENU_DTO_MATCHER.contentJson(MENU_DTO_ALL));
    }

    @Test
    void testGetMenuNotFound() {
        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + MENU_ID_NOT_FOUND))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Menu with id - " + MENU_ID_NOT_FOUND + " does not exist in the database"));
    }

    @Test
    void testUpdateMenuReturnsOk() throws Exception {
        perform(post(path + "/update")
                .content(mapper().writeValueAsString(List.of(MENU_UPDATE_REQUEST)))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        perform(get(path + "/" + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(MENU_ID)))
                .andExpect(MENU_DTO_MATCHER.contentJson(MENU_DTO_UPDATE));

        assertEquals(MENU_ALL_RESTAURANTS_AFTER_UPDATE_MENU_AND_VOTES, voteService.getAllRestaurantWithMenuAndVotes());
    }

    @Test
    void testDeleteMenuReturnsOk() throws Exception {
        perform(post(path + "delete/" + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + MENU_ID))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Menu with id - " + MENU_ID + " does not exist in the database"));
    }

    @Test
    void testDeleteMenuByRestaurantReturnsOk() throws Exception {
        perform(post(path + "delete_by_restaurant/" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        perform(get(path + "all" + MENU_REQUEST_PARAMS_ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MENU_DTO_MATCHER.contentJson(MENU_DTO_AFTER_DELETE_RESTAURANT));
    }
}
