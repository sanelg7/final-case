package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.model.CreditScore;

import java.util.UUID;

public interface CreditLimitApplicationService {

    CreditLimitApplication createCreditLimitApplication(String userTckn, CreditLimitApplication creditLimitApplication);
    CreditLimitApplication approveCreditLimitApplication(UUID creditLimitApplicationId , CreditScore creditScore);

}
