package com.definex.practicum.finalcase.dto.creditlimitapplication;

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
public class CreditLimitApplicationQueryDto {

    @NotBlank(message = "TCKN cannot be empty")
    @Pattern(regexp = "^[0-9]{11}$", message = "TCKN must be exactly 11 characters long, can only contain numeric values")
    private String tckn;

    @NotNull(message = "Date of birth cannot be empty")
    private Date dateOfBirth;
}
