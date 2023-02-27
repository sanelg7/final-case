package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.CreditLimit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CreditLimitRepository extends JpaRepository<CreditLimit, Long> {
    CreditLimit findFirstByOrderById();
}
