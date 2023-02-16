package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.model.CreditLimit;
import com.definex.practicum.finalcase.model.CreditLimitApplication;

public interface CreditLimitService {
    CreditLimit getCreditLimit(Long id);

    /*
        For NORMAL user role. User can create a CreditLimit through an application only.
        Also works for updates as a user can not control their credit limit.
        */
    CreditLimit createCreditLimit(String userTckn, CreditLimitApplication creditLimitApplication) throws EntityNotFoundException;

    /*
        Overloaded creation for ADMIN user role. Admins can create a CreditLimit for
        NORMAL users with user id and CreditLimit object containing the amount of credit.
        */
    CreditLimit createCreditLimit(Long userId, CreditLimit creditLimit) throws EntityNotFoundException;

    void deleteCreditLimit(Long id) throws EntityNotFoundException;
}