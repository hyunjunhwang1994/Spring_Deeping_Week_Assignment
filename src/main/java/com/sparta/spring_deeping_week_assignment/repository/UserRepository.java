package com.sparta.spring_deeping_week_assignment.repository;

import com.sparta.spring_deeping_week_assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
