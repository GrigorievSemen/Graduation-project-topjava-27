package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.web.request.menu.MenuCreateRequest;
import ru.grigoriev.graduationproject.web.request.update.MenuUpdateRequest;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {
    List<MenuDto> create(MenuCreateRequest... menuCreateRequests);

    List<MenuDto> update(MenuUpdateRequest... menuUpdateRequests);

    List<MenuDto> getAll(LocalDate date);

    List<MenuDto> findMenuByRestaurantId(int id, LocalDate date);

    MenuDto findBiId(Integer id);

    void deleteMenuById(int id);

    void deleteMenuByRestaurantId(int id);
}
