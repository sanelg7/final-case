package com.definex.practicum.finalcase.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityCreationException extends RuntimeException{

    private String type;
    private Long id;

    public EntityCreationException(String type, Long id){
        super(String.format("Entity with type :" + type + ", id: " + id + " could not be created."));
        this.id = id;
        this.type = type;
    }


}
