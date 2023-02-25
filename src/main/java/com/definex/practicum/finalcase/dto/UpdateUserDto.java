package com.definex.practicum.finalcase.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    private String password;

    private String firstName;

    private String lastName;

    // Checks if there is something to update
    public boolean hasNonNullField() {
        return password != null || firstName != null || lastName != null;
    }

}
