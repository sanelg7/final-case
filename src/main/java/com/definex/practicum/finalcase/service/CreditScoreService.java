package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.model.CreditScore;

public interface CreditScoreService {

    double generateCreditScoreValue();
    CreditScore createCreditScore(Long userId);
    void deleteCreditScore(Long id);
    CreditScore getCreditScore(Long id);
    CreditScore updateCreditScore(Long id, CreditScore creditScore);

    boolean existsByUser_Tckn(String userTckn);

}
