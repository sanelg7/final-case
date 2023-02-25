package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.dto.user.UserCreditLimitDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.service.CreditLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/credit-limits")
public class BaseCreditLimitController {

    protected final CreditLimitService creditLimitService;

    @Autowired
    public BaseCreditLimitController(CreditLimitService creditLimitService) {
        this.creditLimitService = creditLimitService;
    }

    @GetMapping
    public CustomResponseEntity<CreditLimit> getCreditScore(@RequestBody UserCreditLimitDto userCreditLimitDto){
        try{
            return new CustomResponseEntity<>(creditLimitService.getCreditLimitByTckn(userCreditLimitDto.getTckn()),
                    "Fetched credit score", HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new CustomResponseEntity<>(null, "No credit score found",HttpStatus.BAD_REQUEST);
        }
    }


}
