package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.dto.CreditLimitApplicationDto;
import com.definex.practicum.finalcase.dto.CreditLimitApplicationQueryDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.model.CreditScore;
import org.springframework.transaction.annotation.Transactional;

public interface CreditLimitApplicationService {

    CreditLimitApplication createCreditLimitApplication(CreditLimitApplicationDto creditLimitApplicationDto);
    CreditLimitApplication approveCreditLimitApplication(Long creditLimitApplicationId , CreditScore creditScore);

    @Transactional(readOnly = true)
    CreditLimit getCreditLimitByTckn(CreditLimitApplicationQueryDto creditLimitApplicationQueryDto) throws EntityNotFoundException;
}
