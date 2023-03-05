package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.dto.VoteDto;
import ru.grigoriev.graduationproject.model.Menu;
import ru.grigoriev.graduationproject.model.Vote;
import ru.grigoriev.graduationproject.repository.DishRepository;
import ru.grigoriev.graduationproject.repository.RestaurantRepository;
import ru.grigoriev.graduationproject.repository.UserRepository;
import ru.grigoriev.graduationproject.web.user.request.menu.MenuCreateRequest;

import java.util.List;


@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class VoteMapper {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Mapping(target = "name", expression = "java(restaurantRepository.findById(vote.getRestaurant().getId()).map(v-> v.getName()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
    public abstract VoteDto toDto(Vote vote);
}
