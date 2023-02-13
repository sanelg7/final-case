package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.model.CreditScore;

public interface CreditScoreService {

    double generateCreditScore();
    CreditScore saveCreditScore(Long userId);
    void deleteCreditScore(Long id);
    CreditScore getCreditScoreById(Long id);
    CreditScore updateCreditScore(CreditScore creditScore);
}
