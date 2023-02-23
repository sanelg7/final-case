package com.definex.practicum.finalcase.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EntityCreationException extends RuntimeException{

    private String type;
    private UUID id;
    private String tckn;

    public EntityCreationException(String type, UUID id){
        super(String.format("Entity with type :" + type + ", id: " + id + " could not be created."));
        this.id = id;
        this.type = type;
    }

    // TODO: this does not work
    public EntityCreationException(String type, String tckn){
        super(String.format("User with tckn :" + tckn  + " already exists."));
        this.tckn = tckn;
        this.type = type;
    }

    // TODO: add one for gsm


}
