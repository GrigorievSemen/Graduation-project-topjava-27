package ru.grigoriev.graduationproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.grigoriev.graduationproject.model.Menu;

import java.util.List;
// test query for console H2
/*SELECT DISTINCT   'Restaurant ' || '"' || r.name   || '"' || ' id = ' || r.id ||  ' menu: ' || group_concat (d.name || ' = ' || m.price separator   ', ') || ',
count votes = ' || (select distinct count(*) from vote v where  v.restaurant_id= r.id and CAST(v.created_at as date) = CURDATE() )  FROM MENU m
        join restaurant r on r.id=m.restaurant_id
        join dish d on d.id=m.dish_id
        group by r.name*/
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

    @Query(value = "SELECT DISTINCT   'Restaurant ' || '\"' || r.name   || '\"' || ' id = ' || r.id ||  ' menu: ' || group_concat (d.name || ' = ' || m.price separator   ', ') || '," +
            "count votes = ' || (SELECT count(*) FROM vote v WHERE  v.restaurant_id= r.id AND CAST(v.created_at AS DATE) = CURDATE() )  FROM MENU m" +
            " JOIN restaurant r on r.id=m.restaurant_id" +
            " JOIN dish d on d.id=m.dish_id" +
            " GROUP BY r.name",nativeQuery = true)
    List<String>  findAllForVote();
}
