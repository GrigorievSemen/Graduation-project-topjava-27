package ru.grigoriev.graduationproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.grigoriev.graduationproject.model.Vote;

import java.util.Optional;
import java.util.Set;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id = :id AND CAST(v.created_at as date) = CURDATE()")
   Optional<Vote> findVoteByUserForCheck(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Vote v WHERE v.restaurant.id = :id AND CAST(v.created_at as date) = CURDATE()")
    void deleteAllByRestaurant_Id (@Param("id") int id);
}
