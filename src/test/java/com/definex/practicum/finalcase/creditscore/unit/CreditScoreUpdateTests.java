package com.definex.practicum.finalcase.creditscore.unit;

import com.definex.practicum.finalcase.dto.creditscore.AdminCreditScoreDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.repository.CreditScoreRepository;
import com.definex.practicum.finalcase.service.impl.CreditScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CreditScoreUpdateTests {

    @Mock
    CreditScoreRepository creditScoreRepository;

    @InjectMocks
    CreditScoreServiceImpl creditScoreService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateCreditScoreByAdmin() throws EntityNotFoundException {

        Long id = 1L;
        // setting up the required dto
        AdminCreditScoreDto adminCreditScoreDto = new AdminCreditScoreDto();
        adminCreditScoreDto.setId(id);
        adminCreditScoreDto.setCreditScoreValue(500.0);

        // setting up the credit score to be updated
        CreditScore creditScore = new CreditScore();
        creditScore.setCreditScoreValue(300.0);
        creditScore.setId(id);

        when(creditScoreRepository.findById(id)).thenReturn(Optional.of(creditScore));
        when(creditScoreRepository.existsById(id)).thenReturn(true);
        when(creditScoreRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        creditScore = creditScoreService.updateCreditScore(adminCreditScoreDto);

        assertEquals(creditScore.getId(), adminCreditScoreDto.getId());
        assertEquals(creditScore.getCreditScoreValue(), adminCreditScoreDto.getCreditScoreValue());
    }
}
