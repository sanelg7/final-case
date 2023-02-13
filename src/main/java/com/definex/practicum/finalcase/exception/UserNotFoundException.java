package com.definex.practicum.finalcase.exception;

public class UserNotFoundException extends RuntimeException{

    private Long id;

    public UserNotFoundException(Long id){
        super(String.format("User with id :" + id + " not found"));
        this.id = id;
    }

    public Long getId(){
        return id;
    }
}
