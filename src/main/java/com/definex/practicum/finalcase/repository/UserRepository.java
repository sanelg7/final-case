package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
