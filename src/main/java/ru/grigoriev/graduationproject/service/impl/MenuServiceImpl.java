package ru.grigoriev.graduationproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.mapper.DishMapper;
import ru.grigoriev.graduationproject.mapper.MenuMapper;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.model.Menu;
import ru.grigoriev.graduationproject.repository.DishRepository;
import ru.grigoriev.graduationproject.repository.MenuRepository;
import ru.grigoriev.graduationproject.service.DishService;
import ru.grigoriev.graduationproject.service.MenuService;
import ru.grigoriev.graduationproject.web.user.request.create.MenuCreateRequest;
import ru.grigoriev.graduationproject.web.user.request.delete.DishDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.DishUpdateRequest;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {
    private final MenuRepository repository;
    private final MenuMapper mapper;

    @Transactional
    @Override
    public MenuDto create(MenuCreateRequest menuCreateRequest) {
        Menu menu = mapper.toMenu(menuCreateRequest);
        MenuDto result = mapper.toDto(repository.save(menu));
        log.info("IN create -> menu: {} successfully save", result);
        return result;
    }

//    @Transactional
//    @Override
//    public DishDto update(DishUpdateRequest dishUpdateRequest) {
//        Dish result = getDishByName(dishUpdateRequest.getOld_name());
//        result.setName(dishUpdateRequest.getNew_name());
//        result.setUpdated_at(new Date());
//        Dish updateDish = repository.save(result);
//        log.info("IN update -> dish: {} successfully updated", updateDish);
//        return mapper.toDto(updateDish);
//    }
//
//    @Override
//    public List<DishDto> getAll() {
//        List<Dish> result = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
//        log.info("IN getAll -> {} dishes found", result.size());
//        return mapper.toDtoList(result);
//    }
//
//    @Override
//    public DishDto findByDishName(String name) {
//        Dish result = getDishByName(name);
//        log.info("IN findByDishName -> dish: {} found by dishName: {}", result, name);
//        return mapper.toDto(result);
//    }
//
//    @Override
//    public DishDto findBiId(Integer id) {
//        Optional<Dish> result = Optional.ofNullable(repository.findById(id)
//                .orElseThrow(() ->
//                        new NotFoundException("Dish does not exist in the database")));
//        log.info("IN findBiId -> dish: {} found by id: {}", result, id);
//        return mapper.toDto(result.get());
//    }
//
//    @Transactional
//    @Override
//    public void delete(DishDeleteRequest dishDeleteRequest) {
//        repository.deleteUserByName(dishDeleteRequest.getName());
//        log.info("In delete -> dish with name: {} successfully deleted", dishDeleteRequest.getName());
//    }
//
//    private Dish getDishByName(String name) {
//        Optional<Dish> result = Optional.ofNullable(repository.findByName(name)
//                .orElseThrow(() ->
//                        new NotFoundException("Dish does not exist in the database")));
//        return result.get();
//    }
}
