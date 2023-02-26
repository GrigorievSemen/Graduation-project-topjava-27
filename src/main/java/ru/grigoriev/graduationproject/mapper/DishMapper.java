package ru.grigoriev.graduationproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.model.Dish;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DishMapper {
    @Mappings({
            @Mapping(target="id", source="id")
    })
    DishDto toDto(Dish dish);

    List<DishDto> toDtoList(List<Dish> dishList);

    Dish toDish(DishDto dto);
}
