package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.dto.CreditLimitApplicationDto;
import com.definex.practicum.finalcase.dto.CreditLimitApplicationQueryDto;
import com.definex.practicum.finalcase.exception.CreditLimitApplicationException;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
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

    @PostMapping
    public CustomResponseEntity<CreditLimitApplication> applyForCreditLimit(
            @RequestBody CreditLimitApplicationDto creditLimitApplicationDto){
            try {
                return new CustomResponseEntity<>(
                        creditLimitApplicationService.createCreditLimitApplication(creditLimitApplicationDto),
                        "Credit limit application created successfully", HttpStatus.ACCEPTED);
            }catch (EntityNotFoundException | CreditLimitApplicationException e){
                if(e instanceof EntityNotFoundException){
                    return new CustomResponseEntity<>(null, "No user found with given tckn", HttpStatus.BAD_REQUEST);
                }
                return new CustomResponseEntity<>(null, "Entered values do not match a user", HttpStatus.BAD_REQUEST);
            }

    }

    @PostMapping("/status")
    public CustomResponseEntity<CreditLimit> checkCreditLimitApplicationResult(
            @RequestBody CreditLimitApplicationQueryDto creditLimitApplicationQueryDto){
        try {
            return new CustomResponseEntity<>(
                    creditLimitApplicationService.getCreditLimitByTckn(creditLimitApplicationQueryDto),
                    "Credit limit returned", HttpStatus.ACCEPTED);
        }catch (EntityNotFoundException e){
                return new CustomResponseEntity<>(null, "No user found with given tckn", HttpStatus.BAD_REQUEST);
        }

    }

}
