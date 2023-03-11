package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.VoteDto;

public interface VoteService {

    String getAllRestaurantWithMenuAndVotes();

    VoteDto create(int id, boolean flag);
}
