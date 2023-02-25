package com.definex.practicum.finalcase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @NotBlank(message = "TCKN cannot be empty")
    @Pattern(regexp = "^[0-9]{11}$", message = "TCKN must be exactly 11 characters long, can only contain numeric values")
    private String tckn;

    @NotBlank(message = "password cannot be empty")
    private String password;

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "GSM No cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "GSMno must be exactly 10 characters long, can only contain numeric values")
    private String gsmNumber;

    @NotNull(message = "Date of birth cannot be empty")
    private Date dateOfBirth;

}
