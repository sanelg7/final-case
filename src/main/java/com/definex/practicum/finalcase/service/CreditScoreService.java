package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.model.CreditScore;

import java.util.UUID;

public interface CreditScoreService {

    double generateCreditScoreValue();
    CreditScore createCreditScore(UUID userId);
    void deleteCreditScore(UUID id);
    CreditScore getCreditScore(UUID id);
    CreditScore updateCreditScore(UUID id, CreditScore creditScore);

    boolean existsByUser_Tckn(String userTckn);

}
