package com.definex.practicum.finalcase.exception;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateException extends RuntimeException{

    public UserUpdateException() {
        super(String.format("Nothing found to update."));
    }

}
