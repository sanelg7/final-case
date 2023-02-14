package com.definex.practicum.finalcase.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFoundException extends RuntimeException{

    private String type;
    private Long id;

    private String tckn;
    public EntityNotFoundException(String type, Long id){
        super(String.format("Entity with type :" + type + ", id: " + id + " not found"));
        this.id = id;
        this.type = type;
    }

    public EntityNotFoundException(String type, String tckn){
        super(String.format("User with tckn: " + tckn + " not found"));
        this.type = type;
        this.tckn = tckn;
    }

}
