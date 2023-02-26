package ru.grigoriev.graduationproject.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.model.Restaurant;
import ru.grigoriev.graduationproject.repository.DishRepository;
import ru.grigoriev.graduationproject.repository.RestaurantRepository;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class DB {

    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    public Restaurant getRestaurantByName(String name) {
        Optional<Restaurant> result = Optional.ofNullable(restaurantRepository.findByName(name)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with name - " + name + " does not exist in the database")));
        return result.get();
    }

    public Restaurant getRestaurantById(int id) {
        Optional<Restaurant> result = Optional.ofNullable(restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with id - " + id + " does not exist in the database")));
        return result.get();
    }

    public Dish getDishByName(String name) {
        Optional<Dish> result = Optional.ofNullable(dishRepository.findByName(name)
                .orElseThrow(() ->
                        new NotFoundException("Dish with name - " + name + "  does not exist in the database")));
        return result.get();
    }

    public Dish getDishById(int id) {
        Optional<Dish> result = Optional.ofNullable(dishRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Dish with id - " + id + "  does not exist in the database")));
        return result.get();
    }


}
