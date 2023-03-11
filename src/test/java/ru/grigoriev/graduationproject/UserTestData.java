package ru.grigoriev.graduationproject;

import ru.grigoriev.graduationproject.model.User;


public class UserTestData {

    public static User getNewCorrectUser() {
        return new User("User4", "user4@yandex.com", "test4");
    }

    public static User getNewWithDuplicateEmail() {
        return new User("User4", "user3@yandex.com", "test4");
    }
}
