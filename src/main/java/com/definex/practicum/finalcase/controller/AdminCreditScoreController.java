package com.definex.practicum.finalcase.controller;

import com.definex.practicum.finalcase.dto.AdminCreditScoreDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.model.CustomResponseEntity;
import com.definex.practicum.finalcase.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit-scores/admin")
public class AdminCreditScoreController extends BaseCreditScoreController {

    @Autowired
    public AdminCreditScoreController(CreditScoreService creditScoreService) {
        super(creditScoreService);
    }

    // Only admin can update the credit score by passing a value manually.
    @PutMapping
    public CustomResponseEntity<CreditScore> updateCreditScore(@RequestBody AdminCreditScoreDto creditScoreDto){
        try{
            return new CustomResponseEntity<>(creditScoreService.updateCreditScore(creditScoreDto),
                    "Credit score updated successfully", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new CustomResponseEntity<>(null,
                    "No credit score found to update",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCreditScore(@PathVariable Long id){
        try {
            creditScoreService.deleteCreditScore(id);
            return new ResponseEntity<>("Credit score deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No credit score found to delete", HttpStatus.BAD_REQUEST);
        }
    }
}
