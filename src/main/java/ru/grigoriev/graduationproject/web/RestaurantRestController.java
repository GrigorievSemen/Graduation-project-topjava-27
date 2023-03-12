package ru.grigoriev.graduationproject.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.RestaurantDto;
import ru.grigoriev.graduationproject.mapper.RestaurantMapper;
import ru.grigoriev.graduationproject.model.Restaurant;
import ru.grigoriev.graduationproject.repository.RestaurantRepository;
import ru.grigoriev.graduationproject.util.DB.DB;
import ru.grigoriev.graduationproject.web.constant.Constant;
import ru.grigoriev.graduationproject.web.request.update.RestaurantUpdateRequest;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/admin/restaurants",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantRestController {

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;
    private final DB db;

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody @Valid Restaurant restaurant) {
        log.info("IN createRestaurant");
        Restaurant result = repository.save(restaurant);
        log.info("Restaurant {} successfully save", result);
        return ResponseEntity.ok(mapper.toDto(result));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable(name = "id") int id) {
        log.info("IN getRestaurantById");
        Restaurant result = db.getRestaurantById(id);
        log.info("Restaurant {} found by id: {}", result, id);
        return ResponseEntity.ok(mapper.toDto(result));
    }

    @GetMapping(value = "/")
    public ResponseEntity<RestaurantDto> getRestaurantByName(@RequestParam(value = "name", required = false) String name) {
        log.info("IN getRestaurantByName");
        Restaurant result = db.getRestaurantByName(name);
        log.info("Restaurant {} found by restaurantName: {}", result, name);
        return ResponseEntity.ok(mapper.toDto(result));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<RestaurantDto>> getAll() {
        log.info("IN getAll");
        List<Restaurant> result = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        log.info("Restaurant found {}", result.size());
        return ResponseEntity.ok(mapper.toDtoList(result));
    }

    @Transactional
    @PostMapping(value = "/update")
    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody @Valid RestaurantUpdateRequest restaurantUpdateRequest) {
        log.info("IN updateRestaurant");
        Restaurant result = db.getRestaurantByName(restaurantUpdateRequest.getOld_name());
        result.setName(restaurantUpdateRequest.getNew_name());
        result.setUpdated_at(LocalDateTime.now());
        Restaurant updateRestaurant = repository.save(result);
        log.info("Restaurant {} successfully updated", updateRestaurant);
        return ResponseEntity.ok(mapper.toDto(updateRestaurant));
    }

    @Transactional
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable int id ) {
        log.info("IN deleteRestaurant");
        repository.deleteById(id);
        log.info("Restaurant with id: {} successfully deleted", id);
        return ResponseEntity.ok("Restaurant with id: " + id + ", successfully deleted.");
    }
}
