package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.web.user.request.delete.DishDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.DishUpdateRequest;

import java.util.List;

public interface DishService {
    DishDto create(Dish dish);

    DishDto update(DishUpdateRequest dish);

    List<DishDto> getAll();

    DishDto findByName(String name);

    DishDto findBiId(Integer id);

    void delete(DishDeleteRequest dishUpdateDto);
}
