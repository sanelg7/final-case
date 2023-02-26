package com.definex.practicum.finalcase.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateUpdateCreditLimitDto {

    @NonNull
    @Positive
    private Double amount;

}
