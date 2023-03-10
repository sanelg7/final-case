package com.definex.practicum.finalcase.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateException extends RuntimeException{

    public UserUpdateException() {
        super("No data found to update with.");
    }
    public UserUpdateException(String tckn, String gsmNumber) {
        super("TCKN: " + tckn + " OR Gsm number: " + gsmNumber + " taken!");
    }
}
