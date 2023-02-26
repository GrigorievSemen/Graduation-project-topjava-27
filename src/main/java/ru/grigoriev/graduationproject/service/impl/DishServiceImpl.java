package ru.grigoriev.graduationproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.mapper.DishMapper;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.repository.DishRepository;
import ru.grigoriev.graduationproject.service.DishService;
import ru.grigoriev.graduationproject.util.DB;
import ru.grigoriev.graduationproject.web.user.request.delete.DishDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.DishUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishServiceImpl implements DishService {
    private final DishRepository repository;
    private final DishMapper mapper;
    private final DB db;

    @Transactional
    @Override
    public DishDto create(Dish dish) {
        Dish result = repository.save(dish);
        log.info("IN create -> dish: {} successfully save", result);
        return mapper.toDto(result);
    }

    @Transactional
    @Override
    public DishDto update(DishUpdateRequest dishUpdateRequest) {
        Dish result = db.getDishByName(dishUpdateRequest.getOld_name());
        result.setName(dishUpdateRequest.getNew_name());
        result.setUpdated_at(LocalDateTime.now());
        Dish updateDish = repository.save(result);
        log.info("IN update -> dish: {} successfully updated", updateDish);
        return mapper.toDto(updateDish);
    }

    @Override
    public List<DishDto> getAll() {
        List<Dish> result = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        log.info("IN getAll -> {} dishes found", result.size());
        return mapper.toDtoList(result);
    }

    @Override
    public DishDto findByDishName(String name) {
        Dish result = db.getDishByName(name);
        log.info("IN findByDishName -> dish: {} found by dishName: {}", result, name);
        return mapper.toDto(result);
    }

    @Override
    public DishDto findBiId(Integer id) {
        Dish result = db.getDishById(id);
        log.info("IN findBiId -> dish: {} found by id: {}", result, id);
        return mapper.toDto(result);
    }

    @Transactional
    @Override
    public void delete(DishDeleteRequest dishDeleteRequest) {
        repository.deleteUserByName(dishDeleteRequest.getName());
        log.info("In delete -> dish with name: {} successfully deleted", dishDeleteRequest.getName());
    }
}
