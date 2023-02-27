package com.definex.practicum.finalcase.creditlimit.integration;

import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.CreditLimitApplicationRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import com.definex.practicum.finalcase.service.CreditLimitService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class CreditLimitCreateTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditLimitApplicationRepository creditLimitApplicationRepository;

    @Autowired
    private CreditLimitService creditLimitService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testCreateCreditLimit() {
        // Create required parameters

        User user = userRepository.findFirstByOrderById();
        String userTckn = user.getTckn();

        CreditScore creditScore = new CreditScore();
        creditScore.setUser(user);
        creditScore.setCreditScoreValue(500.0);
        user.setCreditScore(creditScore);

        UUID userId = user.getId();
        CreditLimitApplication creditLimitApplication = new CreditLimitApplication();
        creditLimitApplication.setGuarantee(5000.0);
        creditLimitApplication.setMonthlyIncome(10000.0);
        creditLimitApplicationRepository.save(creditLimitApplication);

        user.addCreditLimitApplication(creditLimitApplication);
        creditLimitApplication.setUser(user);


        // Save the user with creditLimitApplications
        userRepository.save(user);

        CreditLimit createdCreditLimit = creditLimitService.createCreditLimit(userTckn, creditLimitApplication);

        assertNotNull(createdCreditLimit);
        assertNotNull(createdCreditLimit.getAmount());
        assertEquals(userTckn, createdCreditLimit.getUser().getTckn());
        assertEquals(userId, createdCreditLimit.getUser().getId());
    }
    @Test
    public void testCreateCreditLimitByAdmin() {

        // Provide valid parameters
        User user = userRepository.findFirstByOrderById();
        UUID userId = user.getId();

        Double amount = 20000.0;

        CreditLimit creditLimit = new CreditLimit();
        creditLimit.setAmount(amount);
        creditLimit.setUser(user);
        user.setCreditLimit(creditLimit);

        assertNotNull(creditLimit);
        assertNotNull(creditLimit.getAmount());
        assertEquals(userId, creditLimit.getUser().getId());

    }


}

