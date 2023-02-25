package com.definex.practicum.finalcase.service.impl;

import com.definex.practicum.finalcase.dto.CreditLimitApplicationDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.service.CreditLimitApplicationService;
import com.definex.practicum.finalcase.service.CreditLimitService;
import com.definex.practicum.finalcase.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.repository.CreditLimitApplicationRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreditLimitApplicationServiceImpl implements CreditLimitApplicationService {

    private final UserRepository userRepository;
    private final CreditLimitApplicationRepository creditLimitApplicationRepository;
    private final CreditScoreService creditScoreService;
    private final CreditLimitService creditLimitService;

    @Autowired
    public CreditLimitApplicationServiceImpl(UserRepository userRepository,
                                             CreditLimitApplicationRepository creditLimitApplicationRepository,
                                             CreditScoreService creditScoreService, CreditLimitService creditLimitService){

                this.creditLimitApplicationRepository = creditLimitApplicationRepository;
                this.userRepository = userRepository;
                this.creditScoreService = creditScoreService;
                this.creditLimitService = creditLimitService;
    }

    @Override
    public CreditLimitApplication createCreditLimitApplication(CreditLimitApplicationDto creditLimitApplicationDto) throws EntityNotFoundException{
        String userTckn = creditLimitApplicationDto.getTckn();
        if(!userRepository.existsByTckn(userTckn)){
            throw new EntityNotFoundException(User.class.getName(), userTckn);
        }
        User user = userRepository.findByTckn(userTckn).get();
        // TODO: Might need to check this
        if(!creditScoreService.existsByUser_Tckn(userTckn)){
            user.setCreditScore(creditScoreService.createCreditScore(user.getId()));

        }
        CreditLimitApplication creditLimitApplication = new CreditLimitApplication();
        creditLimitApplication.setUser(user);
        creditLimitApplication.setGuarantee(creditLimitApplicationDto.getGuarantee());
        creditLimitApplication.setMonthlyIncome(creditLimitApplicationDto.getMonthlyIncome());
        // Saving te application itself
        creditLimitApplicationRepository.save(creditLimitApplication);
        // Set approval
        creditLimitApplication = approveCreditLimitApplication(creditLimitApplication.getId(), user.getCreditScore());

        if(creditLimitApplication.getApproved()){
            // Generating a CreditLimit with the help of CreditLimitService
            creditLimitService.createCreditLimit(userTckn, creditLimitApplication);

        }else{
            // TODO:Return negative sms.
        }

        return creditLimitApplication;


    }

    // Sets approval, works like a regular update on db
    @Override
    public CreditLimitApplication approveCreditLimitApplication(Long creditLimitApplicationId ,
                                                                CreditScore creditScore) throws EntityNotFoundException{
        if(!creditLimitApplicationRepository.existsById(creditLimitApplicationId)){
            throw new EntityNotFoundException(CreditLimitApplication.class.getName(), creditLimitApplicationId);
        }
        CreditLimitApplication creditLimitApplication = creditLimitApplicationRepository.findById(creditLimitApplicationId).get();
        if(creditScore.getCreditScoreValue()<500){
            creditLimitApplication.setApproved(false);

        }else{
            creditLimitApplication.setApproved(true);
        }
        return creditLimitApplicationRepository.save(creditLimitApplication);
    }



}
