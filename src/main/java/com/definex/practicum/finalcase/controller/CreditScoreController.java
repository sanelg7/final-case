package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/credit-scores")
public class CreditScoreController {

    CreditScoreService creditScoreService;

    @Autowired
    public CreditScoreController(CreditScoreService creditScoreService) {
        this.creditScoreService = creditScoreService;
    }

    @GetMapping
    public CustomResponseEntity<CreditScore> getCreditScore(@RequestParam("id") Long id){
        try{
            return new CustomResponseEntity<>(creditScoreService.getCreditScore(id),
                    "Fetched credit score",HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new CustomResponseEntity<>(null, "No credit score found",HttpStatus.BAD_REQUEST);
        }
    }

    /*
    In the context of this assignment, credit score is a randomly created value.
    There is no need to pass in a CreditScore object within the body. A user id to form the relationship is enough.
    The user id is passed in from the request body to only mimic the usual way (object -> request body).
    */
    @PostMapping
    public CustomResponseEntity<CreditScore> createCreditScore(@RequestBody Long userId){
        try{
            return new CustomResponseEntity<>(creditScoreService.createCreditScore(userId),
                    "Credit score created successfully", HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new CustomResponseEntity<>(null, "No such user found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public CustomResponseEntity<CreditScore> updateCreditScore(@RequestParam("id") Long id, @RequestBody CreditScore creditScore){
        try{
            return new CustomResponseEntity<>(creditScoreService.updateCreditScore(id,creditScore),
                    "Credit score updated successfully",HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new CustomResponseEntity<>(null,
                    "No credit score found to update",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCreditScore(@RequestParam("id") Long id){
        try {
            creditScoreService.deleteCreditScore(id);
            return new ResponseEntity<>("Credit score deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No credit score found to delete", HttpStatus.BAD_REQUEST);
        }
    }
}
