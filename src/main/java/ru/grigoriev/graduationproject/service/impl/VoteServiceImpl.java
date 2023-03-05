package ru.grigoriev.graduationproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.LocalDateType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.dto.VoteDto;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.exception.VotingErrorException;
import ru.grigoriev.graduationproject.mapper.VoteMapper;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.model.Restaurant;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.model.Vote;
import ru.grigoriev.graduationproject.repository.MenuRepository;
import ru.grigoriev.graduationproject.repository.RestaurantRepository;
import ru.grigoriev.graduationproject.repository.UserRepository;
import ru.grigoriev.graduationproject.repository.VoteRepository;
import ru.grigoriev.graduationproject.security.jwt.JwtUser;
import ru.grigoriev.graduationproject.service.VoteService;
import ru.grigoriev.graduationproject.util.DB.DB;
import ru.grigoriev.graduationproject.web.user.request.menu.MenuCreateRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {
    private final MenuRepository menuRepository;
    private final VoteRepository voteRepository;
    private final DB db;
    private final VoteMapper voteMapper;
    private final int hour = 11;

    @Transactional
    @Override
    public VoteDto create(int id, boolean flag) {
        JwtUser userAuth = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Vote> check = voteRepository.findVoteByUserForCheck(userAuth.getId());
        VoteDto result = null;

        if(flag){
            if(check.isEmpty()){
                result = voteMapper.toDto(voteRepository.save(new Vote(db.getUserById(userAuth.getId()),db.getRestaurantById(id))));
                log.info("IN create -> vote: {} successfully save", result);
            }  else  {
                throw new VotingErrorException ("You have already voted today, you can update the data until 11 days (/vote/update).");
            }
        }

        if(!flag) {
            if (check.isEmpty()) {
                throw new VotingErrorException ("You haven't voted yet today!");
            } else if (check.get().getCreated_at().getHour() > hour - 1) {
                throw new VotingErrorException("You have already voted today, it is forbidden to update the data after 11 o'clock in the afternoon.");
            } else {
                check.get().setUpdated_at(LocalDateTime.now());
                check.get().setRestaurant(db.getRestaurantById(id));
                result = voteMapper.toDto(voteRepository.save(check.get()));
                log.info("IN create -> vote: {} successfully update", result);
            }
        }
        return result;
    }

    @Override
    public String getAllRestaurant() {
        List<String> result = menuRepository.findAllForVote();
        StringBuilder sb = new StringBuilder();
        result.forEach(v-> sb.append(v).append("\n"));
        log.info("IN getAll -> {} menu found", sb);
        return sb.toString();
    }

    @Override
    public String getAllVote() {
        List<String> result = menuRepository.findAllForVote();
        StringBuilder sb = new StringBuilder();
        result.forEach(v-> sb.append(v).append("\n"));
        log.info("IN getAll -> {} menu found", sb);
        return sb.toString();
    }
}
