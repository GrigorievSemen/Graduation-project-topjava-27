package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.model.Menu;
import ru.grigoriev.graduationproject.repository.DishRepository;
import ru.grigoriev.graduationproject.repository.RestaurantRepository;
import ru.grigoriev.graduationproject.web.request.menu.MenuCreateRequest;

import java.util.List;


@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class MenuMapper {
    @Autowired
    protected DishRepository dishRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    RestaurantMapper restaurantMapper;
    @Autowired
    DishMapper dishMapper;

    @Mapping(target = "restaurant", expression = "java(restaurantMapper.toDto(restaurantRepository.findById(menu.getRestaurant().getId()).orElseThrow(javax.persistence.EntityNotFoundException::new)))")
    @Mapping(target = "dish", expression = "java(dishMapper.toDto(dishRepository.findById(menu.getDish().getId()).orElseThrow(javax.persistence.EntityNotFoundException::new)))")
    public abstract MenuDto toDto(Menu menu);

    @Mapping(target = "restaurant", expression = "java(restaurantRepository.findById(menuCreateRequest.getRestaurantId()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
    @Mapping(target = "dish", expression = "java(dishRepository.findById(menuCreateRequest.getDishId()).orElseThrow(javax.persistence.EntityNotFoundException::new))")
    public abstract Menu toMenu(MenuCreateRequest menuCreateRequest);

    public abstract List<MenuDto> toDtoList(List<Menu> menuList);
}
