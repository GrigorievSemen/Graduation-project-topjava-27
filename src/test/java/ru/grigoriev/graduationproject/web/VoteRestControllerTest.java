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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.grigoriev.graduationproject.DataTest.*;
import static ru.grigoriev.graduationproject.util.MockSecurity.addMockToken;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

@AutoConfigureWebTestClient
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "test")
public class VoteRestControllerTest extends AbstractControllerTest {
    private final String path = Constant.VERSION_URL + "/votes/";
    @Autowired
    private VoteService voteService;


    @BeforeEach
    public void addToken() {
        addMockToken(ADMIN);
    }

    @Test
    void testGetAllRestaurantWithMenuAndVotesReturnsOk() throws Exception {
        perform(get(path + "/allRestaurant")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(MENU_ALL_RESTAURANTS_AND_VOTES, voteService.getAllRestaurantWithMenuAndVotes());
    }

    @Test
    void testCreateVoteReturnsOk() throws Exception {
        perform(post(path + "/save/" + VOTE_MENU_RESTAURANT)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Vote for restaurant with id = " + VOTE_MENU_RESTAURANT + ", name's restaurant = " + RESTAURANT_NAME + ", successfully create."));

    }

    @Test
    void testReCreateVote() throws Exception {
        testCreateVoteReturnsOk();
        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(post(path + "/save/" + VOTE_MENU_RESTAURANT))
                        .andDo(print()));
        assertThat(exception.getCause().getMessage(), equalTo("You have already voted today, you can update the data until 11 days (/votes/update)."));
    }

    @Test
    void testUpdateVoteReturnsOk() throws Exception {
        addMockToken(USER_FOR_UPDATE_VOTE);
        perform(post(path + "/update/" + VOTE_MENU_RESTAURANT_FOR_UPDATE)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Vote for restaurant with id = " + VOTE_MENU_RESTAURANT_FOR_UPDATE + ", name's restaurant = " + RESTAURANT_NAME_FOR_UPDATE + ", successfully update."));

        assertEquals(MENU_ALL_RESTAURANTS_AND_VOTES_AFTER_UPDATE_VOTE, voteService.getAllRestaurantWithMenuAndVotes());
    }

    @Test
    void testUpdateVoteAfterEleven() {
        addMockToken(USER);
        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(post(path + "/update/" + VOTE_MENU_RESTAURANT))
                        .andDo(print()));
        assertThat(exception.getCause().getMessage(), equalTo("You have already voted today, it is forbidden to update the data after 11 o'clock in the afternoon."));
    }

    @Test
    void testUpdateVoteIfNotVotedYet() {
        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                perform(post(path + "/update/" + VOTE_MENU_RESTAURANT))
                        .andDo(print()));
        assertThat(exception.getCause().getMessage(), equalTo("You haven't voted yet today!"));
    }
}
