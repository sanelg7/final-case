package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.service.CreditLimitApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit-limit-applications")
public class CreditLimitApplicationController {

    CreditLimitApplicationService creditLimitApplicationService;

    @Autowired
    public CreditLimitApplicationController(CreditLimitApplicationService creditLimitApplicationService) {
        this.creditLimitApplicationService = creditLimitApplicationService;
    }

    // TODO: Might delete RequestParam
    @PostMapping
    public CustomResponseEntity<CreditLimitApplication> applyForCreditLimit(
            @RequestParam("tckn") String tckn, @RequestBody CreditLimitApplication creditLimitApplication){
            try {
                return new CustomResponseEntity<>(
                        creditLimitApplicationService.createCreditLimitApplication(tckn, creditLimitApplication),
                        "Credit limit application created successfully", HttpStatus.ACCEPTED);
            }catch (EntityNotFoundException e){
                return new CustomResponseEntity<>(null, "No user found with given tckn", HttpStatus.BAD_REQUEST);
            }

    }
}
