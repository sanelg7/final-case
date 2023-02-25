package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.dto.AdminCreditLimitDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.service.CreditLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit-limits/admin")
public class CreditLimitAdminController extends BaseCreditLimitController{

    @Autowired
    public CreditLimitAdminController(CreditLimitService creditLimitService) {
        super(creditLimitService);
    }

    // Also works as update
    @PostMapping
    public CustomResponseEntity<CreditLimit> createCreditLimit(@RequestBody AdminCreditLimitDto adminCreditLimitDto){

        try {
            return new CustomResponseEntity<>(creditLimitService.createCreditLimitByAdmin(
                    adminCreditLimitDto.getUserId(),adminCreditLimitDto.getAmount()), "Created credit limit successfully", HttpStatus.CREATED);
        }catch (EntityNotFoundException e){
            return new CustomResponseEntity<>(null, "No such user found", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCreditLimit(@PathVariable Long id){
        try {
            creditLimitService.deleteCreditLimit(id);
            return new ResponseEntity<>("Credit score deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No credit score found to delete", HttpStatus.BAD_REQUEST);
        }
    }

}
