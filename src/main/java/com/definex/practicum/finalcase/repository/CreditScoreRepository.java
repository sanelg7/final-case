package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
}
