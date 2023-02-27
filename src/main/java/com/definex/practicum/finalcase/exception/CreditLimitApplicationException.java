package com.definex.practicum.finalcase.exception;


public class CreditLimitApplicationException extends RuntimeException{


    public CreditLimitApplicationException(){
            super("Passed values do not match a user.");
        }

    }
