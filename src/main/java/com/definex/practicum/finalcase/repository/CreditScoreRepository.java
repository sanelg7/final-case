package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {

    boolean existsByUser_Tckn(String userTckn);

    boolean existsById(UUID id);

    Optional<CreditScore> findById(UUID id);
}
