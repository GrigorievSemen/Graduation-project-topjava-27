package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.RestaurantDto;
import ru.grigoriev.graduationproject.model.Restaurant;
import ru.grigoriev.graduationproject.web.user.request.delete.RestaurantDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.RestaurantUpdateRequest;

import java.util.List;

public interface RestaurantService {
    RestaurantDto create(Restaurant restaurant);

    RestaurantDto update(RestaurantUpdateRequest restaurant);

    List<RestaurantDto> getAll();

    RestaurantDto findByName(String name);

    RestaurantDto findBiId(Integer id);

    void delete(RestaurantDeleteRequest restaurant);
}
