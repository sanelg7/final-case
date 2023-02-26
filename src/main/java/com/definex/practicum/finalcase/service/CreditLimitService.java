package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.dto.CreditLimitApplicationQueryDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CreditLimitApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CreditLimitService {

    CreditLimit getCreditLimit(Long id) throws EntityNotFoundException;



    CreditLimit getCreditLimitById(Long id);

    CreditLimit createCreditLimit(String userTckn, CreditLimitApplication creditLimitApplication);

    CreditLimit createCreditLimitByAdmin(UUID userId, Double amount) throws EntityNotFoundException;

    void deleteCreditLimit(Long id) throws EntityNotFoundException;

    CreditLimit getCreditLimitByTckn(CreditLimitApplicationQueryDto creditLimitApplicationQueryDto);
}
