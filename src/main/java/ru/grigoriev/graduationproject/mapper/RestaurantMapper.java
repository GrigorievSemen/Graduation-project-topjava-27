package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.dto.RestaurantDto;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.model.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    @Mappings({
            @Mapping(target="id", source="id")
    })
    RestaurantDto toDto(Restaurant restaurant);

    List<RestaurantDto> toDtoList(List<Restaurant> restaurantList);

    Restaurant toRestaurant(RestaurantDto restaurantDto);
}
