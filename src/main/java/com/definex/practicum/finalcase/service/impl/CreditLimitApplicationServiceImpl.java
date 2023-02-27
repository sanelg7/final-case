package com.definex.practicum.finalcase.service.impl;

import com.definex.practicum.finalcase.dto.creditlimitapplication.CreditLimitApplicationDto;
import com.definex.practicum.finalcase.dto.creditlimitapplication.CreditLimitApplicationQueryDto;
import com.definex.practicum.finalcase.exception.CreditLimitApplicationException;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CreditScore;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.CreditLimitRepository;
import com.definex.practicum.finalcase.service.CreditLimitApplicationService;
import com.definex.practicum.finalcase.service.CreditLimitService;
import com.definex.practicum.finalcase.service.CreditScoreService;
import com.definex.practicum.finalcase.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.repository.CreditLimitApplicationRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class CreditLimitApplicationServiceImpl implements CreditLimitApplicationService {

    private final UserRepository userRepository;
    private final CreditLimitApplicationRepository creditLimitApplicationRepository;
    private final CreditLimitRepository creditLimitRepository;
    private final CreditScoreService creditScoreService;
    private final CreditLimitService creditLimitService;
    private final SmsService smsService;

    @Autowired
    public CreditLimitApplicationServiceImpl(UserRepository userRepository,
                                             CreditLimitApplicationRepository creditLimitApplicationRepository,
                                             CreditScoreService creditScoreService, CreditLimitService creditLimitService,
                                             CreditLimitRepository creditLimitRepository, SmsService smsService){

                this.creditLimitApplicationRepository = creditLimitApplicationRepository;
                this.userRepository = userRepository;
                this.creditScoreService = creditScoreService;
                this.creditLimitService = creditLimitService;
                this.creditLimitRepository = creditLimitRepository;
                this.smsService = smsService;
    }

    @Override
    public CreditLimitApplication createCreditLimitApplication(CreditLimitApplicationDto creditLimitApplicationDto) throws EntityNotFoundException, CreditLimitApplicationException{
        String userTckn = creditLimitApplicationDto.getTckn();
        if(!userRepository.existsByTckn(userTckn)){
            throw new EntityNotFoundException(User.class.getName(), userTckn);
        }

        User user = userRepository.findByTckn(userTckn).get();
        if(!creditScoreService.existsByUser_Tckn(userTckn)){
            user.setCreditScore(creditScoreService.createCreditScore(user.getId()));
        }
        if(!valuesMatchUserDescription(user, creditLimitApplicationDto)){
            throw new CreditLimitApplicationException();
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
            smsService.sendSms(creditLimitApplication, true);
        }else{
            smsService.sendSms(creditLimitApplication, false);
        }

        return creditLimitApplication;
    }

    // Checks if passed in values match an existing user in db.
    public boolean valuesMatchUserDescription(User user, CreditLimitApplicationDto creditLimitApplicationDto) {

        // Formatting both dates from user and creditLimitApplicationDto to ensure there are no formatting issues.
        Date receivedDateOfBirth = creditLimitApplicationDto.getDateOfBirth();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(receivedDateOfBirth);

        if(!userRepository.existsByTckn(user.getTckn())){
            return false;
        }
        // Checking the required fields one by one
        // Use the formatted dates
        return user.getTckn().equals(creditLimitApplicationDto.getTckn())
                && user.getFirstName().equalsIgnoreCase(creditLimitApplicationDto.getFirstName())
                && user.getLastName().equalsIgnoreCase(creditLimitApplicationDto.getLastName())
                && user.getGsmNumber().equals(creditLimitApplicationDto.getGsmNumber())
                && formatter.format(user.getDateOfBirth()).equals(formattedDate);
    }

    // Sets approval, works like a regular update on db
    @Override
    public CreditLimitApplication approveCreditLimitApplication(Long creditLimitApplicationId ,
                                                                CreditScore creditScore) throws EntityNotFoundException{
        if(!creditLimitApplicationRepository.existsById(creditLimitApplicationId)){
            throw new EntityNotFoundException(CreditLimitApplication.class.getName(), creditLimitApplicationId);
        }
        CreditLimitApplication creditLimitApplication = creditLimitApplicationRepository.findById(creditLimitApplicationId).get();
        creditLimitApplication.setApproved(creditScore.getCreditScoreValue() >= 500);
        return creditLimitApplicationRepository.save(creditLimitApplication);
    }


    @Transactional(readOnly = true)
    @Override
    public CreditLimit getCreditLimitApplicationResultByTckn(CreditLimitApplicationQueryDto creditLimitApplicationQueryDto) throws EntityNotFoundException{
        String userTckn = creditLimitApplicationQueryDto.getTckn();
        if(!userRepository.existsByTckn(userTckn)){
            throw new EntityNotFoundException(User.class.getName(), userTckn);
        }
        User user = userRepository.findByTckn(userTckn).get();
        if (!creditLimitRepository.existsById(user.getCreditLimit().getId())){
            throw new EntityNotFoundException(CreditLimit.class.getName());
        }
        // Check if passed tckn and date of birth match.
        Date receivedDateOfBirth = creditLimitApplicationQueryDto.getDateOfBirth();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(formatter.format(user.getDateOfBirth()).equals(formatter.format(receivedDateOfBirth))){
            return creditLimitRepository.findById(user.getCreditLimit().getId()).get();
            // Throwing this as passing "not matched" can be a security concern.
        } else {
            throw new EntityNotFoundException(CreditLimit.class.getName());
        }

    }



}
