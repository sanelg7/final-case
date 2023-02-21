package com.definex.practicum.finalcase.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
public class CustomResponseEntity<T> {

    // Custom Response Entity class that takes in a generic object. Used within controllers to pass in extra information , ie. "message"
    private T responseObject;
    private String message;
    private HttpStatus httpStatus;


}
