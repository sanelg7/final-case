package com.definex.practicum.finalcase.service.impl;

import com.definex.practicum.finalcase.dto.creditlimitapplication.CreditLimitApplicationQueryDto;
import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.CreditLimitApplicationRepository;
import com.definex.practicum.finalcase.repository.CreditLimitRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import com.definex.practicum.finalcase.service.CreditLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class CreditLimitServiceImpl implements CreditLimitService {

    private final CreditLimitRepository creditLimitRepository;
    private final CreditLimitApplicationRepository creditLimitApplicationRepository;
    private final UserRepository userRepository;
    @Autowired
    public CreditLimitServiceImpl(CreditLimitRepository creditLimitRepository,
                                  CreditLimitApplicationRepository creditLimitApplicationRepository,
                                  UserRepository userRepository) {
        this.creditLimitRepository = creditLimitRepository;
        this.creditLimitApplicationRepository = creditLimitApplicationRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public CreditLimit getCreditLimitById(Long id) throws EntityNotFoundException{
        if (!creditLimitRepository.existsById(id)){
            throw new EntityNotFoundException(CreditLimit.class.getName());
        }
        return creditLimitRepository.findById(id).get();
    }


    /*
    For USER role. User can create a CreditLimit through an application only.
    Also works for updates when another approved application is made as a user can not
    set their credit limit manually.
    */
    @Transactional
    @Override
    public CreditLimit createCreditLimit(String userTckn, CreditLimitApplication creditLimitApplication) throws EntityNotFoundException{
        if(!userRepository.existsByTckn(userTckn)){
            throw new EntityNotFoundException(User.class.getName(), userTckn);
        }
        if(!creditLimitApplicationRepository.existsByUser_Tckn(userTckn)){
            throw new EntityNotFoundException(CreditLimitApplication.class.getName(), userTckn);
        }
        User user = userRepository.findByTckn(userTckn).get();

        double creditScore = user.getCreditScore().getCreditScoreValue();
        double income = creditLimitApplication.getMonthlyIncome();
        double guarantee = creditLimitApplication.getGuarantee();
        double creditLimitAmount = generateCreditLimitValue(creditScore, income, guarantee);
        CreditLimit creditLimit = new CreditLimit(creditLimitAmount, user);
        user.setCreditLimit(creditLimit);

        return creditLimitRepository.save(creditLimit);
    }



    /*
    For ADMIN user role. Admins can create/update a CreditLimit for
    users with user id and CreditLimit object containing the amount of credit. Also works as update.
    */
    @Transactional
    @Override
    public CreditLimit createCreditLimitByAdmin(UUID userId, Double amount) throws EntityNotFoundException {
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException(User.class.getName(), userId);
        }
        // No need to check credit limit application here.
        User user = userRepository.findById(userId).get();

        CreditLimit creditLimit = new CreditLimit();

        // Admin can assign limit REGARDLESS of the credit score.
        creditLimit.setAmount(amount);
        creditLimit.setUser(user);
        user.setCreditLimit(creditLimit);
        // This save operation will override if the user has an existing CreditLimit automatically.
        return creditLimitRepository.save(creditLimit);
    }

    @Transactional
    @Override
    public void deleteCreditLimit(Long id) throws EntityNotFoundException{
        if(!creditLimitRepository.existsById(id)){
            throw new EntityNotFoundException(CreditLimit.class.getName(), id);
        }
        creditLimitRepository.deleteById(id);

    }


    // Credit limit calculation
    public Double generateCreditLimitValue(double creditScore, double income, double guarantee){
        int creditLimitMultiplier = 4;
        double creditLimitAmount;

        if (creditScore < 1000) {
            if (income < 5000) {
                creditLimitAmount = 10000;
            } else if (income <= 10000) {
                creditLimitAmount = 20000;
            } else {
                creditLimitAmount = income * creditLimitMultiplier / 2;
            }
        } else {
            creditLimitAmount = income * creditLimitMultiplier;
        }

        if (guarantee > 0) {
            if (creditScore < 1000) {
                if (income < 5000) {
                    creditLimitAmount += guarantee / 10;
                } else if (income <= 10000) {
                    creditLimitAmount += guarantee / 5;
                } else {
                    creditLimitAmount += guarantee / 4;
                }
            } else {
                creditLimitAmount += guarantee / 2;
            }
        }

        return creditLimitAmount;
    }

    // Used by tests
    @Transactional(readOnly = true)
    @Override
    public CreditLimit getCreditLimitByTckn(CreditLimitApplicationQueryDto creditLimitApplicationQueryDto) throws EntityNotFoundException {
        String userTckn = creditLimitApplicationQueryDto.getTckn();
        if(!userRepository.existsByTckn(userTckn)) {
            throw new EntityNotFoundException(User.class.getName(), userTckn);
        }
        User user = userRepository.findByTckn(userTckn).get();
        if(!creditLimitRepository.existsById(user.getCreditLimit().getId())) {
            throw new EntityNotFoundException(CreditLimit.class.getName());
        }
        // Check if passed tckn and date of birth match.
        Date receivedDateOfBirth = creditLimitApplicationQueryDto.getDateOfBirth();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(formatter.format(user.getDateOfBirth()).equals(formatter.format(receivedDateOfBirth))) {
            return creditLimitRepository.findById(user.getCreditLimit().getId()).get();
            // Throwing this as passing "not matched" can be a security concern.
        } else {
            throw new EntityNotFoundException(CreditLimit.class.getName());
        }
    }



}
