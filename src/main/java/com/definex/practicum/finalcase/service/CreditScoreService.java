package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.dto.CreditScoreDto;
import com.definex.practicum.finalcase.model.CreditScore;

import java.util.UUID;

public interface CreditScoreService {

    double generateCreditScoreValue();
    CreditScore createCreditScore(UUID userId);
    void deleteCreditScore(Long id);
    CreditScore getCreditScore(Long id);
    CreditScore updateCreditScore(CreditScoreDto creditScoreDto);

    boolean existsByUser_Tckn(String userTckn);

}
