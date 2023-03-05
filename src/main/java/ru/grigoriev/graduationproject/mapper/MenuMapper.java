package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.model.Menu;
import ru.grigoriev.graduationproject.repository.DishRepository;
import ru.grigoriev.graduationproject.repository.RestaurantRepository;
import ru.grigoriev.graduationproject.web.user.request.menu.MenuCreateRequest;

import java.util.List;


@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class MenuMapper {
    @Autowired
    protected DishRepository dishRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Mapping(target = "restaurant", expression = "java(restaurantRepository.findById(menu.getRestaurant().getId()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
    @Mapping(target = "dish", expression = "java(dishRepository.findById(menu.getDish().getId()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
    public abstract MenuDto toDto(Menu menu);

    @Mapping(target = "restaurant", expression = "java(restaurantRepository.findById(menuCreateRequest.getRestaurant_id()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
    @Mapping(target = "dish", expression = "java(dishRepository.findById(menuCreateRequest.getDish_id()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
    public abstract Menu toMenu(MenuCreateRequest menuCreateRequest);

    public abstract List<MenuDto> toDtoList(List<Menu> menuList);
}
