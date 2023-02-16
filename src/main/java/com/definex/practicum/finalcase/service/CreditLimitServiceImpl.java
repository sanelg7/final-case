package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.CreditLimitApplicationRepository;
import com.definex.practicum.finalcase.repository.CreditLimitRepository;
import com.definex.practicum.finalcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditLimitServiceImpl implements CreditLimitService{

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

    @Override
    public CreditLimit getCreditLimit(Long id) throws EntityNotFoundException{
        if(!creditLimitRepository.existsById(id)){
            throw new EntityNotFoundException(CreditLimit.class.getName(), id);
        }
        return creditLimitRepository.findById(id).get();
    }


    /*
    For NORMAL user role. User can create a CreditLimit through an application only.
    Also works for updates when an approved application is made as a user can not
    set their credit limit manually.
    */
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
        return creditLimitRepository.save(creditLimit);
    }



    /*
    Overloaded creation for ADMIN user role. Admins can create/update a CreditLimit for
    NORMAL users with user id and CreditLimit object containing the amount of credit.
    */
    @Override
    public CreditLimit createCreditLimit(Long userId, CreditLimit creditLimit) throws EntityNotFoundException {
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException(User.class.getName(), userId);
        }
        // No need to check credit limit application here.
        User user = userRepository.findById(userId).get();

        // Admin can assign limit REGARDLESS of the credit score.
        creditLimit.setUser(user);

        // This save operation will override if the user has an existing CreditLimit automatically.
        return creditLimitRepository.save(creditLimit);
    }

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

}
