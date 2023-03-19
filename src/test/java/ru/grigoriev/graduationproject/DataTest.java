package ru.grigoriev.graduationproject;

import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.dto.RestaurantDto;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.*;
import ru.grigoriev.graduationproject.util.MatcherFactory;
import ru.grigoriev.graduationproject.web.request.AuthenticationRequest;
import ru.grigoriev.graduationproject.web.request.delete.UserDeleteRequest;
import ru.grigoriev.graduationproject.web.request.update.DishUpdateRequest;
import ru.grigoriev.graduationproject.web.request.update.RestaurantUpdateRequest;
import ru.grigoriev.graduationproject.web.request.update.UserUpdateRequest;

import java.util.Collections;
import java.util.List;


public class DataTest {
    public static final MatcherFactory.Matcher<UserDto> USER_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserDto.class, "created_at", "updated_at");
    public static final MatcherFactory.Matcher<DishDto> DISH_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishDto.class, "");
    public static final MatcherFactory.Matcher<RestaurantDto> RESTAURANT_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantDto.class, "");

    public static final AuthenticationRequest AUTHENTICATION_REQUEST = AuthenticationRequest.builder().name("Admin").password("admin").build();
    public static final AuthenticationRequest AUTHENTICATION_REQUEST_EXS = AuthenticationRequest.builder().name("Admin").password("adminn").build();

    public static final User USER = User.builder().id(2).name("User1").email("user1@yandex.com").password("test1").enabled(true)
            .status(Status.ACTIVE).roles(Collections.singleton(Role.ROLE_USER)).build();
    public static final User ADMIN = User.builder().id(1).name("Admin").email("admin@yandex.com").password("admin").enabled(true)
            .status(Status.ACTIVE).roles(Collections.singleton(Role.ROLE_ADMIN)).build();
    public static final List<UserDto> ALL_USER_DTO = List.of(UserDto.builder().id(1).name("Admin").email("admin@yandex.com")
                    .roles(Collections.singleton(Role.ROLE_ADMIN)).build(),
            (UserDto.builder().id(2).name("User1").email("user1@yandex.com").roles(Collections.singleton(Role.ROLE_USER)).build()),
            (UserDto.builder().id(3).name("User2").email("user2@yandex.com").roles(Collections.singleton(Role.ROLE_USER)).build()),
            (UserDto.builder().id(4).name("User3").email("user3@yandex.com").roles(Collections.singleton(Role.ROLE_USER)).build()));
    public static final UserUpdateRequest USER_UPDATE_REQUEST = UserUpdateRequest.builder().old_name("User1").new_name("User4")
            .old_password("test1").new_password("test1").email("user1@yandex.com").build();
    public static final UserDeleteRequest USER_DELETE_REQUEST = UserDeleteRequest.builder().id(2).name("User1").password("test1").build();

    public static final Dish DISH = Dish.builder().id(1).name("Chicken").build();
    public static final List<DishDto> ALL_DISH_DTO = List.of(DishDto.builder().id(1).name("Chicken").build(),
            DishDto.builder().id(8).name("Coffee").build(),
            DishDto.builder().id(6).name("Juice").build(),
            DishDto.builder().id(4).name("Pasta").build(),
            DishDto.builder().id(3).name("Potato").build(),
            DishDto.builder().id(2).name("Salad").build(),
            DishDto.builder().id(5).name("Soap").build(),
            DishDto.builder().id(7).name("Tea").build());
    public static final DishUpdateRequest DISH_UPDATE_REQUEST = DishUpdateRequest.builder().old_name("Chicken").new_name("NewChicken").build();

    public static final Restaurant RESTAURANT = Restaurant.builder().id(1).name("By sea").build();
    public static final List<RestaurantDto> ALL_RESTAURANT_DTO = List.of(RestaurantDto.builder().id(1).name("By sea").build(),
            RestaurantDto.builder().id(3).name("Caucasus").build(),
            RestaurantDto.builder().id(2).name("Fairy tale").build(),
            RestaurantDto.builder().id(4).name("Friday 13th").build(),
            RestaurantDto.builder().id(5).name("Friends").build());
    public static final RestaurantUpdateRequest RESTAURANT_UPDATE_REQUEST = RestaurantUpdateRequest.builder().old_name("By sea").new_name("New By sea").build();

    public static final int NEW_USER_ID = 5;
    public static final int USER_ID_NOT_FOUND = 5;
    public static final int NEW_DISH_ID = 9;
    public static final int DISH_ID_NOT_FOUND = 9;
    public static final int NEW_RESTAURANT_ID = 6;
    public static final int RESTAURANT_ID_NOT_FOUND = 6;

    public static User getNewUser() {
        return new User("User4", "user4@yandex.com", "test4");
    }

    public static User getNewUserWithDuplicateEmail() {
        return new User("User4", "user3@yandex.com", "test4");
    }

    public static Dish getNewDish() {
        return new Dish("newDish");
    }

    public static Dish getDuplicateDish() {
        return new Dish("Chicken");
    }

    public static Restaurant getNewRestaurant() {
        return new Restaurant("newRestaurant");
    }

    public static Restaurant getDuplicateRestaurant() {
        return new Restaurant("By sea");
    }
}
