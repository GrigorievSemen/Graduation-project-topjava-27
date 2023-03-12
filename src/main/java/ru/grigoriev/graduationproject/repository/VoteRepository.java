package ru.grigoriev.graduationproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.grigoriev.graduationproject.model.Vote;
import ru.grigoriev.graduationproject.web.response.AllRestaurantWithVote;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id = :id AND CAST(v.created_at as date) = CURDATE()")
    Optional<Vote> findVoteByUserForCheck(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Vote v WHERE v.restaurant.id = :id AND CAST(v.created_at as date) = CURDATE()")
    void deleteAllByRestaurant_Id(@Param("id") int id);

    //    https://stackoverflow.com/questions/52591535/spring-jpa-no-converter-found-capable-of-converting-from-type
    @Query(value = "SELECT r.id id, r.name, group_concat (d.name || ' = ' || m.price separator   ', ') dish, " +
            " ( SELECT count(*) FROM vote v WHERE  v.restaurant_id= m.restaurant_id AND CAST(v.created_at AS DATE) = CURDATE() ) vote" +
            " FROM MENU m " +
            " JOIN restaurant r on r.id=m.restaurant_id" +
            " JOIN dish d on d.id=m.dish_id" +
            " WHERE CAST(m.dat AS DATE) = CURDATE()" +
            " GROUP BY r.name", nativeQuery = true)
    List<AllRestaurantWithVote> findAllRestaurantWithVote();
}
