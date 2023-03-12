package ru.grigoriev.graduationproject.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.mapper.DishMapper;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.repository.DishRepository;
import ru.grigoriev.graduationproject.util.DB.DB;
import ru.grigoriev.graduationproject.web.constant.Constant;
import ru.grigoriev.graduationproject.web.request.update.DishUpdateRequest;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/admin/dish",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishRestController {
    private final DishRepository repository;
    private final DishMapper mapper;
    private final DB db;

    @Transactional
    @PostMapping("/save")
    public ResponseEntity<DishDto> createDish(@RequestBody @Valid Dish dish) {
        log.info("IN createDish");
        Dish result = repository.save(dish);
        log.info("Dish: {} successfully save", result);
        return ResponseEntity.ok(mapper.toDto(result));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DishDto> getDishById(@PathVariable(name = "id") int id) {
        log.info("IN getDishById");
        Dish result = db.getDishById(id);
        log.info("Dish: {} found by id: {}", result, id);
        return ResponseEntity.ok(mapper.toDto(result));
    }

    @GetMapping(value = "/")
    public ResponseEntity<DishDto> getDishByName(@RequestParam(value = "name", required = false) String name) {
        log.info("IN getDishByName");
        Dish result = db.getDishByName(name);
        log.info("Dish: {} found by Name: {}", result, name);
        return ResponseEntity.ok(mapper.toDto(result));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<DishDto>> getAll() {
        log.info("IN getAll");
        List<Dish> result = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        log.info("Dish found {}", result.size());
        return ResponseEntity.ok(mapper.toDtoList(result));
    }

    @Transactional
    @PostMapping(value = "/update")
    public ResponseEntity<DishDto> updateDish(@RequestBody @Valid DishUpdateRequest dishUpdateRequest) {
        log.info("IN updateDish");
        Dish result = db.getDishByName(dishUpdateRequest.getOld_name());
        result.setName(dishUpdateRequest.getNew_name());
        result.setUpdated_at(LocalDateTime.now());
        Dish updateDish = repository.save(result);
        log.info("Dish {} successfully updated", updateDish);
        return ResponseEntity.ok(mapper.toDto(updateDish));
    }

    @Transactional
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable int id) {
        log.info("IN deleteDish");
        repository.deleteById(id);
        log.info("Dish with id: {} successfully deleted", id);
        return ResponseEntity.ok("Dish with id: " + id + ", successfully deleted.");
    }
}
