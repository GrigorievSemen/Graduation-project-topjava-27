package ru.grigoriev.graduationproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.grigoriev.graduationproject.model.Menu;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Modifying
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :id ORDER BY m.price")
    List<Menu> findAll(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.restaurant.id = :id")
    int deleteByRestaurantId(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id = :id")
    int deleteById(@Param("id") int id);
}
