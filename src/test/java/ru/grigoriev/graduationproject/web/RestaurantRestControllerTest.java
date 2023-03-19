package ru.grigoriev.graduationproject.web;

import org.junit.jupiter.api.BeforeEach;
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
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.model.Restaurant;
import ru.grigoriev.graduationproject.web.constant.Constant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
public class RestaurantRestControllerTest extends AbstractControllerTest {
    private final String path = Constant.VERSION_URL + "/admin/restaurants";

    @BeforeEach
    public void addToken() {
        addMockToken(ADMIN);
    }

    @Test
    void testCreateDishReturnsOk() throws Exception {
        Restaurant createRestaurant = getNewRestaurant();

        perform(post(path + "/save")
                .content(mapper().writeValueAsString(createRestaurant))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(NEW_RESTAURANT_ID)))
                .andExpect(jsonPath("$.name", equalTo(getNewRestaurant().getName())));

        perform(get(path + "/" + NEW_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(NEW_RESTAURANT_ID)))
                .andExpect(jsonPath("$.name", equalTo(getNewRestaurant().getName())));
    }

    @Test
    void testCreateExceptionUnique() {
        Restaurant createRestaurant = getDuplicateRestaurant();

        assertThrows(NestedServletException.class,
                () -> perform(post(path + "/save")
                        .content(mapper().writeValueAsString(createRestaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print()));
    }

    @Test
    void testGetDishByIdReturnsOk() throws Exception {
        perform(get(path + "/" + RESTAURANT.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(RESTAURANT.getId())))
                .andExpect(jsonPath("$.name", equalTo(RESTAURANT.getName())));
    }

    @Test
    void testGetDishByNameReturnsOk() throws Exception {
        perform(get(path + "/" + "?name=" + RESTAURANT.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(RESTAURANT.getId())))
                .andExpect(jsonPath("$.name", equalTo(RESTAURANT.getName())));
    }

    @Test
    void testGetDishNotFound() {

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + RESTAURANT_ID_NOT_FOUND))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Restaurant with id - " + RESTAURANT_ID_NOT_FOUND + " does not exist in the database"));
    }

    @Test
    void testGetAllDishReturnsOk() throws Exception {
        perform(get(path + "/all" ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_DTO_MATCHER.contentJson(ALL_RESTAURANT_DTO));
    }

    @Test
    void testUpdateUserReturnsOk() throws Exception {

        perform(post(path + "/update" )
                .content(mapper().writeValueAsString(RESTAURANT_UPDATE_REQUEST))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(RESTAURANT.getId())))
                .andExpect(jsonPath("$.name", equalTo(RESTAURANT_UPDATE_REQUEST.getNew_name())));
    }

    @Test
    void testDeleteDishReturnsOk() throws Exception {

        perform(post(path + "/delete/" + RESTAURANT.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + RESTAURANT.getId()))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Restaurant with id - " + RESTAURANT.getId() + " does not exist in the database"));
    }
}
