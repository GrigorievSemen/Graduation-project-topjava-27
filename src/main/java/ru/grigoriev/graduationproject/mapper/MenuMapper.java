package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.model.Menu;
import ru.grigoriev.graduationproject.repository.DishRepository;
import ru.grigoriev.graduationproject.repository.RestaurantRepository;
import ru.grigoriev.graduationproject.web.user.request.create.MenuCreateRequest;

import java.awt.*;


@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class MenuMapper {
    @Autowired
    protected DishRepository dishRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Mapping(source = "dish.id", target = "dish_id")
    @Mapping(source = "restaurant.id", target = "restaurant_id")
    public abstract MenuDto toDto(Menu menu);

//    public abstract List<MenuItemTO> toDtoList(List<MenuItem> entityList);
//
//    @Mapping(target = "dish", expression = "java(dishRepository.findById(dto.getDishId()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
//    @Mapping(target = "restaurant", expression = "java(restaurantRepository.findById(dto.getRestaurantId()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
//    public abstract MenuItem toEntity(MenuItemTO dto);
//
    @Mapping(target = "dish", expression = "java(dishRepository.findById(menuCreateRequest.getDish_id()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
    @Mapping(target = "restaurant", expression = "java(restaurantRepository.findById(menuCreateRequest.getRestaurant_id()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
    public abstract Menu toMenu(MenuCreateRequest menuCreateRequest);
}
