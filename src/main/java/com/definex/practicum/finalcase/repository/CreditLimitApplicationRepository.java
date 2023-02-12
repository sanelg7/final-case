package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.CreditLimitApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditLimitApplicationRepository extends JpaRepository<CreditLimitApplication, Long> {
}
