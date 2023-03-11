package ru.grigoriev.graduationproject.web.response;

//    https://stackoverflow.com/questions/52591535/spring-jpa-no-converter-found-capable-of-converting-from-type
public interface AllRestaurantWithVote {
    Integer getId();

    String getName();

    String getDish();

    Integer getVote();
}
