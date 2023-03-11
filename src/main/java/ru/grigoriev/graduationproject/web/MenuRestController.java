package ru.grigoriev.graduationproject.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.service.MenuService;
import ru.grigoriev.graduationproject.web.constant.Constant;
import ru.grigoriev.graduationproject.web.request.menu.MenuCreateRequest;
import ru.grigoriev.graduationproject.web.request.update.MenuUpdateRequest;

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
    public ResponseEntity<List<MenuDto>> createMenu(@RequestBody @Valid MenuCreateRequest... menuCreateRequests) {
        log.info("IN createMenu");
        List<MenuDto> result = service.create(menuCreateRequests);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MenuDto> getMenuById(@PathVariable(name = "id") int id) {
        log.info("IN getMenuById");
        return ResponseEntity.ok(service.findBiId(id));
    }

    @GetMapping(value = "/restaurant/{id}")
    public ResponseEntity<List<MenuDto>> getMenuByRestaurantId(@PathVariable(name = "id") int id) {
        log.info("IN getMenuByRestaurantId");
        return ResponseEntity.ok(service.findMenuByRestaurantId(id));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<MenuDto>> getAll() {
        log.info("IN getAll");
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping(value = "/update")
    public ResponseEntity<List<MenuDto>> updateMenu(@RequestBody @Valid MenuUpdateRequest... menuUpdateRequest) {
        log.info("IN updateMenu");
        List<MenuDto> result = service.update(menuUpdateRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteMenuById(@PathVariable(name = "id") int id) {
        log.info("IN deleteMenuById");
        service.deleteMenuById(id);
        return ResponseEntity.ok("Menu with id = " + id + " successfully deleted.");
    }

    @PostMapping(value = "/delete_by_restaurant/{id}")
    public ResponseEntity<String> deleteMenuByRestaurantId(@PathVariable(name = "id") int id) {
        log.info("IN deleteMenuByRestaurantId");
        service.deleteMenuByRestaurantId(id);
        return ResponseEntity.ok("Menu with restaurant_id = " + id + " successfully deleted.");
    }
}
