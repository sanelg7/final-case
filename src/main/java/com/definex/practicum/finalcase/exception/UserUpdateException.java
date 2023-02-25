package com.definex.practicum.finalcase.exception;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateException extends RuntimeException{

    public UserUpdateException() {
        super(String.format("No data found to update with."));
    }
    public UserUpdateException(String tckn, String gsmNumber) {
        super(String.format("TCKN: " + tckn + " OR Gsm number: " + gsmNumber + " taken!"));
    }
}
