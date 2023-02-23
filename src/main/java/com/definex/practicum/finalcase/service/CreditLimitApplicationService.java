package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.dto.CreditLimitApplicationDto;
import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.model.CreditScore;

import java.util.UUID;

public interface CreditLimitApplicationService {

    CreditLimitApplication createCreditLimitApplication(CreditLimitApplicationDto creditLimitApplicationDto);
    CreditLimitApplication approveCreditLimitApplication(Long creditLimitApplicationId , CreditScore creditScore);
}
