package ru.grigoriev.graduationproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grigoriev.graduationproject.dto.MenuDto;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.mapper.MenuMapper;
import ru.grigoriev.graduationproject.model.Menu;
import ru.grigoriev.graduationproject.repository.MenuRepository;
import ru.grigoriev.graduationproject.repository.VoteRepository;
import ru.grigoriev.graduationproject.service.MenuService;
import ru.grigoriev.graduationproject.util.DB.DB;
import ru.grigoriev.graduationproject.web.request.menu.MenuCreateRequest;
import ru.grigoriev.graduationproject.web.request.update.MenuUpdateRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final VoteRepository voteRepository;
    private final MenuMapper mapper;
    private final DB db;

    @Transactional
    @Override
    public List<MenuDto> create(MenuCreateRequest... menuCreateRequests) {
        List<MenuDto> result = Arrays.stream(menuCreateRequests)
                .map(mapper::toMenu)
                .map(m -> mapper.toDto(menuRepository.save(m)))
                .toList();
        log.info("IN create -> menu: {} successfully save", result);
        return result;
    }

    @Transactional
    @Override
    public List<MenuDto> update(MenuUpdateRequest... menuUpdateRequests) {
        List<MenuDto> result = Arrays.stream(menuUpdateRequests)
                .map(m -> {
                    Menu menu = getMenuById(m.getId());
                    menu.setUpdated_at(LocalDateTime.now());
                    menu.setDish(db.getDishById(m.getDish_id()));
                    menu.setRestaurant(db.getRestaurantById(m.getRestaurant_id()));
                    menu.setPrice(m.getPrice());
                    return menu;
                })
                .map(m -> {
                            voteRepository.deleteAllByRestaurant_Id(m.getRestaurant().getId());
                            return mapper.toDto(menuRepository.save(m));
                        }
                )
                .toList();

        log.info("IN update -> menu: {} successfully updated", result);
        return result;
    }


    @Override
    public List<MenuDto> getAll() {
        List<Menu> result = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "restaurant_id"));
        log.info("IN getAll -> {} menu found", result.size());
        return mapper.toDtoList(result);
    }

    @Override
    public List<MenuDto> findMenuByRestaurantId(int id) {
        List<Menu> result = menuRepository.findAll(id);
        log.info("IN findMenuByRestaurantId -> menu: {} found by restaurant's id: {}", result, id);
        return mapper.toDtoList(result);
    }

    @Override
    public MenuDto findBiId(Integer id) {
        Optional<Menu> result = Optional.ofNullable(menuRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Menu does not exist in the database")));
        log.info("IN findBiId -> menu: {} found by id: {}", result, id);
        return mapper.toDto(result.get());
    }

    @Transactional
    @Override
    public void deleteMenuById(int id) {
        if (menuRepository.deleteById(id) > 0) {
            log.info("In deleteMenuById -> menu by id: {} successfully deleted", id);
        } else {
            throw new NotFoundException("Menu does not exist in the database");
        }
    }

    @Transactional
    @Override
    public void deleteMenuByRestaurantId(int id) {
        if (menuRepository.deleteByRestaurantId(id) > 0) {
            log.info("In deleteMenuByRestaurantId -> menu by restaurant_id: {} successfully deleted", id);
        } else {
            throw new NotFoundException("Restaurant does not exist in the database");
        }
    }

    private Menu getMenuById(int id) {
        Optional<Menu> result = Optional.ofNullable(menuRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Menu with id = " + id + " does not exist in the database")));
        return result.get();
    }
}
