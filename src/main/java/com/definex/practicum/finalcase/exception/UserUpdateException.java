package com.definex.practicum.finalcase.exception;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateException extends RuntimeException{

    String tckn;
    Date dateOfBirth;
    String gsmNumber;

    public UserUpdateException(String tckn, Date dateOfBirth, String gsmNumber) {
        super(String.format("tckn, dateOfBirth and gsmNumber can not be changed."
        + "Passed in: tckn=" + tckn + " , gsmNumber=" + gsmNumber + " , dateOfBirth=" + dateOfBirth));
        this.tckn=tckn;
        this.dateOfBirth=dateOfBirth;
        this.gsmNumber=gsmNumber;
    }

}
