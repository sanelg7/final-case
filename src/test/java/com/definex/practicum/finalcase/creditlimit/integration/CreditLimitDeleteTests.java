package com.definex.practicum.finalcase.creditlimit.integration;

import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.repository.CreditLimitRepository;
import com.definex.practicum.finalcase.service.CreditLimitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class CreditLimitDeleteTests {

    @Autowired
    private CreditLimitRepository creditLimitRepository;

    @Autowired
    private CreditLimitService creditLimitService;

    @Test
    public void testDeleteCreditLimit() {
        // Retrieve credit limit from db
        CreditLimit creditLimit = creditLimitRepository.findFirstByOrderById();
        // delete the credit score
        creditLimitService.deleteCreditLimit(creditLimit.getId());

        // assert the result
        assertFalse(creditLimitRepository.existsById(creditLimit.getId()));
    }
}
