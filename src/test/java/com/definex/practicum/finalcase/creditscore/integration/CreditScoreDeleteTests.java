package com.definex.practicum.finalcase.creditscore.integration;

import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.repository.CreditScoreRepository;
import com.definex.practicum.finalcase.service.CreditScoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class CreditScoreDeleteTests {

    @Autowired
    private CreditScoreRepository creditScoreRepository;

    @Autowired
    private CreditScoreService creditScoreService;



    @Test
    public void testDeleteCreditScore() {

        // Retrieve credit score from db
        CreditScore creditScore = creditScoreRepository.findFirstByOrderById();
        // delete the credit score
        creditScoreService.deleteCreditScore(creditScore.getId());

        // assert the result
        assertFalse(creditScoreRepository.existsById(creditScore.getId()));
    }
}
