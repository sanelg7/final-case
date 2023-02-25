package com.definex.practicum.finalcase.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordDto {

    private String password;

    // Checks if there is something to update with
    public boolean hasData() {
        return password != null;    }

}
