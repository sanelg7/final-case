package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.CreditLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CreditLimitRepository extends JpaRepository<CreditLimit, Long> {
    boolean existsById(UUID id);

    Optional<CreditLimit> findById(UUID id);

    void deleteById(UUID id);
}
