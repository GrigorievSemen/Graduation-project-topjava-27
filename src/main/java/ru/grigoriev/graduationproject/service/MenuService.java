package ru.grigoriev.graduationproject.service;

import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.web.user.request.update.MenuUpdateRequest;

import java.util.List;

public interface MenuService {
    List<MenuDto> create(MenuDto... menuDto);

    List<MenuDto> update(MenuUpdateRequest... menuUpdateRequests);

    List<MenuDto> getAll();

    List<MenuDto> findMenuByRestaurantId(int id);

    MenuDto findBiId(Integer id);

    void deleteMenuById(int id);

    void deleteMenuByRestaurantId(int id);
}
