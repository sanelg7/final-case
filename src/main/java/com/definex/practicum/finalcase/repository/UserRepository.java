package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByTckn(String tckn);
    boolean existsByTckn(String tckn);
    User findFirstByOrderById();
    boolean existsByGsmNumber(String gsmNumber);

    Optional<User> findByGsmNumber(String gsmNumber);
}
