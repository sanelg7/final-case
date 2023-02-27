package com.definex.practicum.finalcase.creditscore.integration;

import com.definex.practicum.finalcase.dto.creditscore.AdminCreditScoreDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.repository.CreditScoreRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import com.definex.practicum.finalcase.service.CreditScoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CreditScoreUpdateTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditScoreRepository creditScoreRepository;

    @Autowired
    private CreditScoreService creditScoreService;

    @Test
    public void testUpdateCreditScore(){

        // Find a valid creditScore on db first
        CreditScore creditScore = creditScoreRepository.findFirstByOrderById();
        // create required dto
        AdminCreditScoreDto adminCreditScoreDto = new AdminCreditScoreDto();
        adminCreditScoreDto.setCreditScoreValue(1000.0);
        // enter a valid id
        adminCreditScoreDto.setId(creditScore.getId());

        // call update method on credit score
        CreditScore updatedCreditScore = creditScoreService.updateCreditScore(adminCreditScoreDto);

        // assert the result
        assertNotNull(updatedCreditScore);
        assertEquals(adminCreditScoreDto.getId(), updatedCreditScore.getId());
        assertEquals(adminCreditScoreDto.getCreditScoreValue(), updatedCreditScore.getCreditScoreValue());


    }

    @Test
    public void testUpdateCreditScoreWithInvalidId(){
        // provide an invalid id
        Long invalidId = 0L;

        // Create an adminCreditScoreDto with the invalid id
        AdminCreditScoreDto adminCreditScoreDto = new AdminCreditScoreDto();
        adminCreditScoreDto.setId(invalidId);
        adminCreditScoreDto.setCreditScoreValue(500.0);

        assertThrows(EntityNotFoundException.class, () -> creditScoreService.updateCreditScore(adminCreditScoreDto));

    }
}
