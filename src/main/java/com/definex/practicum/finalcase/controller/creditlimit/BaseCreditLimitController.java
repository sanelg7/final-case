package com.definex.practicum.finalcase.controller.creditlimit;

import com.definex.practicum.finalcase.aop.annotations.RequiresUserRolePermission;
import com.definex.practicum.finalcase.dto.creditlimit.GetCreditLimitDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.service.CreditLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping("/credit-limits")
public class BaseCreditLimitController {

    protected final CreditLimitService creditLimitService;

    @Autowired
    public BaseCreditLimitController(CreditLimitService creditLimitService) {
        this.creditLimitService = creditLimitService;
    }

    @GetMapping("{userId}")
    @RequiresUserRolePermission
    public CustomResponseEntity<CreditLimit> getCreditLimit(@PathVariable UUID userId, @RequestBody GetCreditLimitDto userCreditLimitDto){
        try{
            return new CustomResponseEntity<>(creditLimitService.getCreditLimitById(userCreditLimitDto.getId()),
                    "Fetched credit score", HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new CustomResponseEntity<>(null, "No credit score found",HttpStatus.BAD_REQUEST);
        }
    }


}
