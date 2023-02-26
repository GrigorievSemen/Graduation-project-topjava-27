package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.model.Menu;
import ru.grigoriev.graduationproject.web.user.request.create.MenuCreateRequest;
import ru.grigoriev.graduationproject.web.user.request.delete.DishDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.DishUpdateRequest;

import java.util.List;

public interface MenuService {
    MenuDto create(MenuCreateRequest menu);

//    DishDto update(DishUpdateRequest dish);
//
//    List<DishDto> getAll();
//
//    DishDto findByDishName(String name);
//
//    DishDto findBiId(Integer id);
//
//    void delete(DishDeleteRequest dishUpdateDto);
}
