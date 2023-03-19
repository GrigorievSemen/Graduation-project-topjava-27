package ru.grigoriev.graduationproject;

import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.dto.RestaurantDto;
import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.*;
import ru.grigoriev.graduationproject.util.MatcherFactory;
import ru.grigoriev.graduationproject.web.request.AuthenticationRequest;
import ru.grigoriev.graduationproject.web.request.delete.UserDeleteRequest;
import ru.grigoriev.graduationproject.web.request.menu.MenuCreateRequest;
import ru.grigoriev.graduationproject.web.request.update.DishUpdateRequest;
import ru.grigoriev.graduationproject.web.request.update.MenuUpdateRequest;
import ru.grigoriev.graduationproject.web.request.update.RestaurantUpdateRequest;
import ru.grigoriev.graduationproject.web.request.update.UserUpdateRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


public class DataTest {
    public static final MatcherFactory.Matcher<UserDto> USER_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserDto.class, "created_at", "updated_at");
    public static final MatcherFactory.Matcher<DishDto> DISH_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishDto.class, "");
    public static final MatcherFactory.Matcher<RestaurantDto> RESTAURANT_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantDto.class, "");
    public static final MatcherFactory.Matcher<MenuDto> MENU_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuDto.class, "id");

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

    public static final MenuDto MENU_DTO = MenuDto.builder().restaurant(RestaurantDto.builder().name("By sea").id(1).build())
            .dish(DishDto.builder().name("Chicken").id(1).build()).price(200).dayMenu(LocalDate.now()).build();
    public static final MenuDto MENU_DTO_UPDATE = MenuDto.builder().restaurant(RestaurantDto.builder().name("By sea").id(1).build())
            .dish(DishDto.builder().name("Chicken").id(1).build()).price(300).dayMenu(LocalDate.now()).build();
    public static final List<MenuDto> MENU_DTO_BY_RESTAURANT = List.of(MenuDto.builder().restaurant(RestaurantDto.builder().name("By sea").id(1).build())
                    .dish(DishDto.builder().name("Chicken").id(1).build()).price(200).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("By sea").id(1).build())
                    .dish(DishDto.builder().name("Salad").id(2).build()).price(159.99).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("By sea").id(1).build())
                    .dish(DishDto.builder().name("Coffee").id(8).build()).price(100).dayMenu(LocalDate.now()).build());
    public static final List<MenuDto> MENU_DTO_ALL = List.of(
            MenuDto.builder().restaurant(RestaurantDto.builder().name("By sea").id(1).build())
                    .dish(DishDto.builder().name("Chicken").id(1).build()).price(200).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("By sea").id(1).build())
                    .dish(DishDto.builder().name("Salad").id(2).build()).price(159.99).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("By sea").id(1).build())
                    .dish(DishDto.builder().name("Coffee").id(8).build()).price(100).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Fairy tale").id(2).build())
                    .dish(DishDto.builder().name("Potato").id(3).build()).price(160).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Fairy tale").id(2).build())
                    .dish(DishDto.builder().name("Salad").id(2).build()).price(140.5).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Fairy tale").id(2).build())
                    .dish(DishDto.builder().name("Tea").id(7).build()).price(50).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Fairy tale").id(2).build())
                    .dish(DishDto.builder().name("Coffee").id(8).build()).price(120).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Caucasus").id(3).build())
                    .dish(DishDto.builder().name("Potato").id(3).build()).price(240.99).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Caucasus").id(3).build())
                    .dish(DishDto.builder().name("Pasta").id(4).build()).price(365).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Caucasus").id(3).build())
                    .dish(DishDto.builder().name("Juice").id(6).build()).price(150).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friday 13th").id(4).build())
                    .dish(DishDto.builder().name("Pasta").id(4).build()).price(299.5).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friday 13th").id(4).build())
                    .dish(DishDto.builder().name("Tea").id(7).build()).price(70).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Chicken").id(1).build()).price(180).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Salad").id(2).build()).price(175).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Potato").id(3).build()).price(199.99).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Juice").id(6).build()).price(90).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Coffee").id(8).build()).price(140).dayMenu(LocalDate.now()).build());
    public static final List<MenuDto> MENU_DTO_AFTER_DELETE_RESTAURANT = List.of(
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Fairy tale").id(2).build())
                    .dish(DishDto.builder().name("Potato").id(3).build()).price(160).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Fairy tale").id(2).build())
                    .dish(DishDto.builder().name("Salad").id(2).build()).price(140.5).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Fairy tale").id(2).build())
                    .dish(DishDto.builder().name("Tea").id(7).build()).price(50).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Fairy tale").id(2).build())
                    .dish(DishDto.builder().name("Coffee").id(8).build()).price(120).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Caucasus").id(3).build())
                    .dish(DishDto.builder().name("Potato").id(3).build()).price(240.99).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Caucasus").id(3).build())
                    .dish(DishDto.builder().name("Pasta").id(4).build()).price(365).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Caucasus").id(3).build())
                    .dish(DishDto.builder().name("Juice").id(6).build()).price(150).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friday 13th").id(4).build())
                    .dish(DishDto.builder().name("Pasta").id(4).build()).price(299.5).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friday 13th").id(4).build())
                    .dish(DishDto.builder().name("Tea").id(7).build()).price(70).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Chicken").id(1).build()).price(180).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Salad").id(2).build()).price(175).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Potato").id(3).build()).price(199.99).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Juice").id(6).build()).price(90).dayMenu(LocalDate.now()).build(),
            MenuDto.builder().restaurant(RestaurantDto.builder().name("Friends").id(5).build())
                    .dish(DishDto.builder().name("Coffee").id(8).build()).price(140).dayMenu(LocalDate.now()).build());
    public static final MenuUpdateRequest MENU_UPDATE_REQUEST = MenuUpdateRequest.builder().id(1).restaurant_id(1).dish_id(1).price(300).build();
    public static final String MENU_ALL_RESTAURANT_AFTER_UPDATE = """
            Restaurant id = 1. Menu: Chicken = 300.0, Salad = 159.99, Coffee = 100.0. Votes = 0.
            Restaurant id = 3. Menu: Potato = 240.99, Pasta = 365.0, Juice = 150.0. Votes = 1.
            Restaurant id = 2. Menu: Potato = 160.0, Salad = 140.5, Tea = 50.0, Coffee = 120.0. Votes = 0.
            Restaurant id = 4. Menu: Pasta = 299.5, Tea = 70.0. Votes = 0.
            Restaurant id = 5. Menu: Chicken = 180.0, Salad = 175.0, Potato = 199.99, Juice = 90.0, Coffee = 140.0. Votes = 0.
            """;

    public static final int NEW_USER_ID = 5;
    public static final int USER_ID_NOT_FOUND = 5;
    public static final int NEW_DISH_ID = 9;
    public static final int DISH_ID_NOT_FOUND = 9;
    public static final int RESTAURANT_ID = 1;
    public static final int NEW_RESTAURANT_ID = 6;
    public static final int RESTAURANT_ID_NOT_FOUND = 6;
    public static final int MENU_ID = 1;
    public static final int NEW_MENU_ID = 18;
    public static final int MENU_ID_NOT_FOUND = 18;
    public static final String MENU_REQUEST_PARAMS_BY_RESTAURANT = "?id=1&date=" + LocalDate.now();
    public static final String MENU_REQUEST_PARAMS_ALL = "?date=" + LocalDate.now();

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

    public static MenuCreateRequest getNewMenuCreateRequest() {
        return new MenuCreateRequest(1, 1, 200, LocalDate.now());
    }
}
