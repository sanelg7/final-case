package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.CreditLimitApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CreditLimitApplicationRepository extends JpaRepository<CreditLimitApplication, Long> {

    boolean existsByUser_Tckn(String userTckn);

}
