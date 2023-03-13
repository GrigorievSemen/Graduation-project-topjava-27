package ru.grigoriev.graduationproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.grigoriev.graduationproject.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findAllByRestaurant_IdAndDat(int id, LocalDate date);

    List<Menu> findAllByDatOrderByRestaurantIdAsc(LocalDate date);

    int deleteByRestaurantId(int id);

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id = :id")
    int deleteById(@Param("id") int id);
}
