package ru.grigoriev.graduationproject.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.dto.RestaurantDto;
import ru.grigoriev.graduationproject.model.Menu;
import ru.grigoriev.graduationproject.model.Restaurant;
import ru.grigoriev.graduationproject.service.MenuService;
import ru.grigoriev.graduationproject.service.RestaurantService;
import ru.grigoriev.graduationproject.web.user.constant.Constant;
import ru.grigoriev.graduationproject.web.user.request.create.MenuCreateRequest;
import ru.grigoriev.graduationproject.web.user.request.delete.RestaurantDeleteRequest;
import ru.grigoriev.graduationproject.web.user.request.update.RestaurantUpdateRequest;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/admin/menu",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {

    private final MenuService service;

    public MenuRestController(MenuService menuService) {
        this.service = menuService;
    }

    @PostMapping("/save")
    public ResponseEntity<MenuDto> createMenu(@RequestBody MenuCreateRequest menu) {
        log.info("IN createMenu");
        MenuDto result = service.create(menu);
        return ResponseEntity.ok(result);
    }

//    @GetMapping(value = "/{id}")
//    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable(name = "id") int id) {
//        log.info("IN getRestaurantById");
//        return  ResponseEntity.ok(service.findBiId(id));
//    }
//
//    @GetMapping(value = "/")
//    public ResponseEntity<RestaurantDto> getRestaurantByName(@RequestParam(value = "name", required = false) String name) {
//        log.info("IN getRestaurantByName");
//        return  ResponseEntity.ok(service.findByDishName(name));
//    }
//
//    @GetMapping(value = "/all")
//    public ResponseEntity<List<RestaurantDto>> getAll() {
//        log.info("IN getAll");
//        return  ResponseEntity.ok(service.getAll());
//    }
//
//    @PostMapping(value = "/update")
//    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody @Valid RestaurantUpdateRequest restaurantUpdateRequest) {
//        log.info("IN updateRestaurant");
//        RestaurantDto result = service.update(restaurantUpdateRequest);
//        return ResponseEntity.ok(result);
//    }
//
//    @PostMapping(value = "/delete")
//    public ResponseEntity<String> deleteRestaurant(@RequestBody @Valid RestaurantDeleteRequest restaurantDeleteRequest) {
//        log.info("IN deleteRestaurant");
//        service.delete(restaurantDeleteRequest);
//        return ResponseEntity.ok("Restaurant " + restaurantDeleteRequest.getName() + " successfully deleted.");
//    }
}
