package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.dto.CreditScoreDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/credit-scores")
public class BaseCreditScoreController {

    // Controller both for USER and ADMIN roles

    CreditScoreService creditScoreService;

    @Autowired
    public BaseCreditScoreController(CreditScoreService creditScoreService) {
        this.creditScoreService = creditScoreService;
    }

    @GetMapping
    public CustomResponseEntity<CreditScore> getCreditScore(@RequestBody CreditScoreDto creditScoreDto){
        try{
            return new CustomResponseEntity<>(creditScoreService.getCreditScore(creditScoreDto.getId()),
                    "Fetched credit score", HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new CustomResponseEntity<>(null, "No credit score found",HttpStatus.BAD_REQUEST);
        }
    }

    // In the context of this assignment, credit score is a randomly created value. So only the user id is enough to create and bind to a user. Also, acts as an update.
    @PostMapping("{userId}")
    public CustomResponseEntity<CreditScore> generateCreditScore(@PathVariable UUID userId){
        try{
            return new CustomResponseEntity<>(creditScoreService.createCreditScore(userId),
                    "Credit score created successfully", HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new CustomResponseEntity<>(null, "No such user found", HttpStatus.BAD_REQUEST);
        }
    }



}
