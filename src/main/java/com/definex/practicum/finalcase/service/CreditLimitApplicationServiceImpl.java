package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.repository.CreditLimitApplicationRepository;
import com.definex.practicum.finalcase.repository.UserRepository;

@Service
public class CreditLimitApplicationServiceImpl implements CreditLimitApplicationService{

    private final UserRepository userRepository;
    private final CreditLimitApplicationRepository creditLimitApplicationRepository;
    private final CreditScoreService creditScoreService;

    @Autowired
    public CreditLimitApplicationServiceImpl(UserRepository userRepository,
                                            CreditLimitApplicationRepository creditLimitApplicationRepository,
                                             CreditScoreService creditScoreService){

                this.creditLimitApplicationRepository = creditLimitApplicationRepository;
                this.userRepository = userRepository;
                this.creditScoreService = creditScoreService;
    }

    @Override
    public CreditLimitApplication createCreditLimitApplication(String userTckn, CreditLimitApplication creditLimitApplication) throws EntityNotFoundException{
        if(!userRepository.existsByTckn(userTckn)){
            throw new EntityNotFoundException(User.class.getName(), userTckn);
        }
        User user = userRepository.findByTckn(userTckn).get();
        // TODO: Might need to check this
        if(!creditScoreService.existsByUserTckn(userTckn)){
            creditScoreService.createCreditScore(user.getId());
        }
        creditLimitApplication.setUser(user);
        if(creditLimitApplication.getApproved()){
            // TODO:Call CreditLimitService generateCreditLimit

        }else{
            // TODO:Return negative sms.
        }
        return creditLimitApplication;

    }

    // Works like a regular update.
    @Override
    public CreditLimitApplication approveCreditLimitApplication(Long creditLimitApplicationId ,
                                                                CreditScore creditScore) throws EntityNotFoundException{
        if(!creditLimitApplicationRepository.existsById(creditLimitApplicationId)){
            throw new EntityNotFoundException(CreditLimitApplication.class.getName(), creditLimitApplicationId);
        }
        CreditLimitApplication creditLimitApplication = creditLimitApplicationRepository.findById(creditLimitApplicationId).get();
        if(creditScore.getCreditScore()<500){
            creditLimitApplication.setApproved(false);

        }else{
            creditLimitApplication.setApproved(true);
        }
        return creditLimitApplication;
    }

}
