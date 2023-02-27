package com.definex.practicum.finalcase.controller.creditscore;

import com.definex.practicum.finalcase.service.CreditScoreService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCreditScoreController extends BaseCreditScoreController {


    public UserCreditScoreController(CreditScoreService creditScoreService) {
        super(creditScoreService);
    }
}
