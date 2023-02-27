package com.definex.practicum.finalcase.creditscore.unit;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.repository.CreditScoreRepository;
import com.definex.practicum.finalcase.service.impl.CreditScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class CreditScoreGetTests {


        @Mock
        private CreditScoreRepository creditScoreRepository;

        @InjectMocks
        private CreditScoreServiceImpl creditScoreService;

        @BeforeEach
        public void initMocks() {
            MockitoAnnotations.openMocks(this);
        }


    @Test
    public void testGetCreditScore() throws EntityNotFoundException {
            // create a sample credit score
        CreditScore creditScore = new CreditScore();
        Long id = 1L;
        creditScore.setId(id);

            // set up the creditScoreRepository mock
        when(creditScoreRepository.findById(id)).thenReturn(Optional.of(creditScore));
        when(creditScoreRepository.existsById(id)).thenReturn(true);

            // call the method being tested
            CreditScore returnedCreditScore = creditScoreService.getCreditScore(id);

            // assert the result
            assertEquals(creditScore, returnedCreditScore);
        }

        @Test
        public void testGetCreditScoreWithInvalidId() throws EntityNotFoundException {
            // set up the creditScoreRepository mock
            Long invalidId = 0L;
            when(creditScoreRepository.existsById(invalidId)).thenReturn(false);

            // call the method being tested and expect an EntityNotFoundException
            assertThrows(EntityNotFoundException.class, () -> creditScoreService.getCreditScore(invalidId));
        }

}
