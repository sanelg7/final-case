package com.definex.practicum.finalcase.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditLimitApplicationDto {

    @NotBlank(message = "TCKN cannot be empty")
    @Pattern(regexp = "^[0-9]{11}$", message = "TCKN must be exactly 11 characters long, can only contain numeric values")
    private String tckn;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    @NotNull
    @DecimalMin("0.0")
    private Double monthlyIncome;

    @DecimalMin("0.0")
    private Double guarantee;
}
