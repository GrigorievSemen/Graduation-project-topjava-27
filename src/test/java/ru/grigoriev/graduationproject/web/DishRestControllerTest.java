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
public class DishRestControllerTest extends AbstractControllerTest {
    private final String path = Constant.VERSION_URL + "/admin/dish";

    @BeforeEach
    public void addToken() {
        addMockToken(ADMIN);
    }

    @Test
    void testCreateDishReturnsOk() throws Exception {
        Dish createDish = getNewDish();

        perform(post(path + "/save")
                .content(mapper().writeValueAsString(createDish))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(NEW_DISH_ID)))
                .andExpect(jsonPath("$.name", equalTo(getNewDish().getName())));

        perform(get(path + "/" + NEW_DISH_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(NEW_DISH_ID)))
                .andExpect(jsonPath("$.name", equalTo(getNewDish().getName())));
    }

    @Test
    void testCreateExceptionUnique() {
        Dish createDish = getDuplicateDish();

        assertThrows(NestedServletException.class,
                () -> perform(post(path + "/save")
                        .content(mapper().writeValueAsString(createDish))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print()));
    }

    @Test
    void testGetDishByIdReturnsOk() throws Exception {
        perform(get(path + "/" + DISH.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(DISH.getId())))
                .andExpect(jsonPath("$.name", equalTo(DISH.getName())));
    }

    @Test
    void testGetDishByNameReturnsOk() throws Exception {
        perform(get(path + "/" + "?name=" + DISH.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(DISH.getId())))
                .andExpect(jsonPath("$.name", equalTo(DISH.getName())));
    }

    @Test
    void testGetDishNotFound() {
        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + DISH_ID_NOT_FOUND))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Dish with id - " + DISH_ID_NOT_FOUND + "  does not exist in the database"));
    }

    @Test
    void testGetAllDishReturnsOk() throws Exception {
        perform(get(path + "/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DISH_DTO_MATCHER.contentJson(ALL_DISH_DTO));
    }

    @Test
    void testUpdateUserReturnsOk() throws Exception {
        perform(post(path + "/update")
                .content(mapper().writeValueAsString(DISH_UPDATE_REQUEST))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(DISH.getId())))
                .andExpect(jsonPath("$.name", equalTo(DISH_UPDATE_REQUEST.getNew_name())));
    }

    @Test
    void testDeleteDishReturnsOk() throws Exception {
        perform(post(path + "/delete/" + DISH.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(get(path + "/" + DISH.getId()))
                        .andDo(print()));

        assertThat(exception.getCause().getMessage(), equalTo("Dish with id - " + DISH.getId() + "  does not exist in the database"));
    }
}
