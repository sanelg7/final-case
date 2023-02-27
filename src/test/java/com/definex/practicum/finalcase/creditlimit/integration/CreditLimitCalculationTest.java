package com.definex.practicum.finalcase.creditlimit.integration;

import com.definex.practicum.finalcase.service.impl.CreditLimitServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreditLimitCalculationTest {


    @Autowired
    private CreditLimitServiceImpl creditLimitService;

    @Test
    public void testGenerateCreditLimitValue() {
        // Arrange
        double creditScore = 800;
        double income = 12000;
        double guarantee = 500;
        double expectedCreditLimit = 24125.0;

        // Act
        Double actualCreditLimit = creditLimitService.generateCreditLimitValue(creditScore, income, guarantee);

        // Assert
        assertEquals(expectedCreditLimit, actualCreditLimit, 0.1);
    }
}