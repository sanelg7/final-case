package com.definex.practicum.finalcase.exception;

import com.definex.practicum.finalcase.model.User;
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

    // Specific to when searching a user by their tckn
    public EntityNotFoundException(String type, String tckn){
        super(type.equals(User.class.getName()) ? String.format("User with tckn: %s not found", tckn)
                : String.format("Entity bound to user with tckn: %s not found", tckn));
        this.type = type;
        this.tckn = tckn;
    }

}
