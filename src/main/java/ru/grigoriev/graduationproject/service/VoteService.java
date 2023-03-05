package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.VoteDto;

public interface VoteService {

    String getAllRestaurant();

    String getAllVote();

    VoteDto create(int id, boolean flag);
}
