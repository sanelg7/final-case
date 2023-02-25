package com.definex.practicum.finalcase.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EntityCreationException extends RuntimeException{

    private String type;
    private UUID id;
    private Long longId;
    // strVariable is mostly used for unique String identifiers. Such as user tckn.
    private String strVariable;

    public EntityCreationException(String type, UUID id){
        super(String.format("Entity with type :" + type + ", id: " + id + " could not be created."));
        this.id = id;
        this.type = type;
    }


    public EntityCreationException(String type, String strVariable){
        super(String.format("Record with unique field: " + strVariable + " already exists."));
        this.strVariable = strVariable;
        this.type = type;
    }




}
