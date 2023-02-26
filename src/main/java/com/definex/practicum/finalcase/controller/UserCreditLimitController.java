package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.service.CreditLimitService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCreditLimitController extends BaseCreditLimitController{
    public UserCreditLimitController(CreditLimitService creditLimitService) {
        super(creditLimitService);
    }
}
