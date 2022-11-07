package ru.grigoriev.graduationproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.grigoriev.graduationproject.model.User;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<User, Integer> {
}
