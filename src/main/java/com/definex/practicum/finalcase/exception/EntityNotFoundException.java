package com.definex.practicum.finalcase.exception;

import org.springframework.data.util.QTypeContributor;

public class EntityNotFoundException extends RuntimeException{

    private String type;
    private Long id;

    public EntityNotFoundException(String type, Long id){
        super(String.format("Entity with type :" + type + ", id: " + id + " not found"));
        this.id = id;
        this.type = type;
    }

    public Long getId(){
        return id;
    }
}
