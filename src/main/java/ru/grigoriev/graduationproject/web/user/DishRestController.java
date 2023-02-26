package ru.grigoriev.graduationproject.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.DishDto;
import ru.grigoriev.graduationproject.model.Dish;
import ru.grigoriev.graduationproject.service.DishService;
import ru.grigoriev.graduationproject.web.user.constant.Constant;
import ru.grigoriev.graduationproject.web.user.request.delete.DishDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.DishUpdateRequest;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/admin/dish",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {

    private final DishService service;

    public DishRestController(DishService dishService) {
        this.service = dishService;
    }

    @PostMapping("/save")
    public ResponseEntity<DishDto> createDish(@RequestBody @Valid Dish dish) {
        log.info("IN createDish");
        DishDto result = service.create(dish);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DishDto> getDishById(@PathVariable(name = "id") int id) {
        log.info("IN getDishById");
        return ResponseEntity.ok(service.findBiId(id));
    }

    @GetMapping(value = "/")
    public ResponseEntity<DishDto> getDishByName(@RequestParam(value = "name", required = false) String name) {
        log.info("IN getDishByName");
        return ResponseEntity.ok(service.findByDishName(name));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<DishDto>> getAll() {
        log.info("IN getAll");
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping(value = "/update")
    public ResponseEntity<DishDto> updateDish(@RequestBody @Valid DishUpdateRequest dishUpdate) {
        log.info("IN updateDish");
        DishDto result = service.update(dishUpdate);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<String> deleteDish(@RequestBody @Valid DishDeleteRequest dishDeleteRequest) {
        log.info("IN deleteDish");
        service.delete(dishDeleteRequest);
        return ResponseEntity.ok("Dish " + dishDeleteRequest.getName() + " successfully deleted.");
    }
}
