package ru.grigoriev.graduationproject;

import ru.grigoriev.graduationproject.dto.UserDto;
import ru.grigoriev.graduationproject.model.Role;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.web.MatcherFactory;
import ru.grigoriev.graduationproject.web.request.delete.UserDeleteRequest;
import ru.grigoriev.graduationproject.web.request.update.UserUpdateRequest;

import java.util.*;


public class UserTestData {
    public static final MatcherFactory.Matcher<UserDto> USER_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserDto.class, "created_at","updated_at");

    public static final int ADMIN_ID = 1;
    public static final int USER_ID = 2;
    public static final int USER_ID_NOT_FOUND = 5;
    public static final String USER_NAME = "User1";
    public static final String USER_EMAIL = "user1@yandex.com";
    public static final List<String> USER_ROLE = new ArrayList<>(Collections.singleton(Role.ROLE_USER.toString()));
    public static final User USER_FOR_TOKEN = new User(2,"User1","user1@yandex.com","test1",true, Collections.singleton(Role.ROLE_USER));

    public static User getUser() {
        return new User(2,"User1","user1@yandex.com","test1",true, Collections.singleton(Role.ROLE_USER));
    }

    public static User getAdmin() {
        return new User(1,"Admin","admin@yandex.com","admin",true, Collections.singleton(Role.ROLE_ADMIN));
    }

    public static List<UserDto> getAllUserDto() {
        return List.of(UserDto.builder().id(1).name("Admin").email("admin@yandex.com").roles(Collections.singleton(Role.ROLE_ADMIN)).build(),
                (UserDto.builder().id(2).name("User1").email("user1@yandex.com").roles(Collections.singleton(Role.ROLE_USER)).build()),
                (UserDto.builder().id(3).name("User2").email("user2@yandex.com").roles(Collections.singleton(Role.ROLE_USER)).build()),
                (UserDto.builder().id(4).name("User3").email("user3@yandex.com").roles(Collections.singleton(Role.ROLE_USER)).build()));
    }

    public static User getNewCorrectUser() {
        return new User("User4", "user4@yandex.com", "test4");
    }

    public static User getNewUserWithDuplicateEmail() {
        return new User("User4", "user3@yandex.com", "test4");
    }

    public static UserUpdateRequest getUserUpdateRequest() {
        return  UserUpdateRequest.builder().old_name("User1").new_name("User4").old_password("test1")
                .new_password("test4").email("user1@yandex.com").build();
    }

    public static UserDeleteRequest getUserDeleteRequest() {
        return  UserDeleteRequest.builder().id(2).name("User1").password("test1").build();
    }
}
