package com.definex.practicum.finalcase.creditlimit.unit;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.repository.CreditLimitRepository;
import com.definex.practicum.finalcase.service.impl.CreditLimitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CreditLimitGetTests {

    @Mock
    private CreditLimitRepository creditLimitRepository;

    @InjectMocks
    private CreditLimitServiceImpl creditLimitService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCreditLimit() throws EntityNotFoundException{
        // create a sample credit limit

        CreditLimit creditLimit = new CreditLimit();
        Long id = 1L;
        creditLimit.setId(id);

        // set up the creditLimitRepository mock
        when(creditLimitRepository.findById(id)).thenReturn(Optional.of(creditLimit));
        when(creditLimitRepository.existsById(id)).thenReturn(true);

        CreditLimit returnedCreditLimit = creditLimitService.getCreditLimitById(id);
        assertEquals(creditLimit, returnedCreditLimit);

    }

    @Test
    public void testGetCreditLimitWithInvalidId() throws EntityNotFoundException {
        // set up the creditScoreRepository mock
        Long invalidId = 2L;
        when(creditLimitRepository.existsById(invalidId)).thenReturn(false);

        // call the method being tested and expect an EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> creditLimitService.getCreditLimitById(invalidId));
    }
}
