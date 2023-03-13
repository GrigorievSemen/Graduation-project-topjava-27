package ru.grigoriev.graduationproject;

import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.model.Role;
import ru.grigoriev.graduationproject.model.Status;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.web.MatcherFactory;
import ru.grigoriev.graduationproject.web.request.AuthenticationRequest;
import ru.grigoriev.graduationproject.web.request.delete.UserDeleteRequest;
import ru.grigoriev.graduationproject.web.request.update.UserUpdateRequest;

import java.util.*;


public class UserTestData {
    public static final MatcherFactory.Matcher<UserDto> USER_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserDto.class, "created_at", "updated_at");
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
    public static final int USER_ID_NOT_FOUND = 5;
    public static final int USER_ID = 5;
    public static final int DISH_ID = 9;


    public static User getNewUser() {
        return new User("User4", "user4@yandex.com", "test4");
    }

    public static User getNewUserWithDuplicateEmail() {
        return new User("User4", "user3@yandex.com", "test4");
    }

    public static Dish getNewDish() {
        return new Dish("newDish");
//        Dish.builder().name("newDish").build();
    }

    public static Dish getDuplicateDish() {
        return new Dish("Chicken");
    }
}
