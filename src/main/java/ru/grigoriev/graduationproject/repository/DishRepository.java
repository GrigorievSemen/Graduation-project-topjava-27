package ru.grigoriev.graduationproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.grigoriev.graduationproject.model.Dish;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    Optional<Dish> findByName(String name);

    void deleteUserByName(String name);
}
