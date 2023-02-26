package com.definex.practicum.finalcase.exception;

import java.util.UUID;

public class CreditLimitApplicationException extends RuntimeException{


    public CreditLimitApplicationException(){
            super(String.format("Passed values do not match a user."));
        }

    }
