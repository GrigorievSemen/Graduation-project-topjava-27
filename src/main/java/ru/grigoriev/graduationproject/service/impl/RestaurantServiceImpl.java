package ru.grigoriev.graduationproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.RestaurantDto;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.mapper.RestaurantMapper;
import ru.grigoriev.graduationproject.model.Restaurant;
import ru.grigoriev.graduationproject.repository.RestaurantRepository;
import ru.grigoriev.graduationproject.service.RestaurantService;
import ru.grigoriev.graduationproject.web.user.request.delete.RestaurantDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.RestaurantUpdateRequest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;

    @Transactional
    @Override
    public RestaurantDto create(Restaurant restaurant) {
        Restaurant result = repository.save(restaurant);
        log.info("IN create -> restaurant: {} successfully save", result);
        return mapper.toDto(result);
    }

    @Transactional
    @Override
    public RestaurantDto update(RestaurantUpdateRequest restaurantUpdateRequest) {
        Restaurant result = getRestaurantByName(restaurantUpdateRequest.getOld_name());
        result.setName(restaurantUpdateRequest.getNew_name());
        result.setUpdated_at(LocalDateTime.now());
        Restaurant updateRestaurant = repository.save(result);
        log.info("IN update -> restaurant: {} successfully updated", updateRestaurant);
        return mapper.toDto(updateRestaurant);
    }

    @Override
    public List<RestaurantDto> getAll() {
        List<Restaurant> result = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        log.info("IN getAll -> {} restaurant found", result.size());
        return mapper.toDtoList(result);
    }

    @Override
    public RestaurantDto findByDishName(String name) {
        Restaurant result = getRestaurantByName(name);
        log.info("IN findByDishName -> restaurant: {} found by restaurantName: {}", result, name);
        return mapper.toDto(result);
    }

    @Override
    public RestaurantDto findBiId(Integer id) {
        Optional<Restaurant> result = Optional.ofNullable(repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant does not exist in the database")));
        log.info("IN findBiId -> restaurant: {} found by id: {}", result, id);
        return mapper.toDto(result.get());
    }

    @Transactional
    @Override
    public void delete(RestaurantDeleteRequest restaurantDeleteRequest) {
        repository.deleteUserByName(restaurantDeleteRequest.getName());
        log.info("In delete -> restaurant with name: {} successfully deleted", restaurantDeleteRequest.getName());
    }

    private Restaurant getRestaurantByName(String name) {
        Optional<Restaurant> result = Optional.ofNullable(repository.findByName(name)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant does not exist in the database")));
        return result.get();
    }
}
