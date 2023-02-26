package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.dto.AdminCreateUpdateCreditLimitDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.service.CreditLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/credit-limits/admin")
public class AdminCreditLimitController extends BaseCreditLimitController {

    @Autowired
    public AdminCreditLimitController(CreditLimitService creditLimitService) {
        super(creditLimitService);
    }

    // Also works as update
    @PostMapping("{userId}")
    public CustomResponseEntity<CreditLimit> createCreditLimit(@PathVariable UUID userId, @RequestBody AdminCreateUpdateCreditLimitDto adminCreateUpdateCreditLimitDto){

        try {
            return new CustomResponseEntity<>(creditLimitService.createCreditLimitByAdmin(
                    userId, adminCreateUpdateCreditLimitDto.getAmount()), "Created credit limit successfully", HttpStatus.CREATED);
        }catch (EntityNotFoundException e){
            return new CustomResponseEntity<>(null, "No such user found", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCreditLimit(@PathVariable Long id){
        try {
            creditLimitService.deleteCreditLimit(id);
            return new ResponseEntity<>("Credit limit deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No credit limit found to delete", HttpStatus.BAD_REQUEST);
        }
    }

}
