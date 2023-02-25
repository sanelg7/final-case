package com.definex.practicum.finalcase.dto;

import com.definex.practicum.finalcase.model.CreditLimitApplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreditLimitDto {

    private CreditLimitApplication creditLimitApplication;

    @NotBlank(message = "TCKN cannot be empty")
    @Pattern(regexp = "^[0-9]{11}$", message = "TCKN must be exactly 11 characters long, can only contain numeric values")
    private String tckn;
}
