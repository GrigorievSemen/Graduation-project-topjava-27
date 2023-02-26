package ru.grigoriev.graduationproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.grigoriev.graduationproject.model.Menu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Modifying
    @Query("SELECT m FROM Menu m where m.restaurant.id = :id order by m.price")
    List<Menu> findAll(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Menu m where m.restaurant.id = :id")
    int deleteByRestaurantId(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Menu m where m.id = :id")
    int deleteById(@Param("id") int id);
}
