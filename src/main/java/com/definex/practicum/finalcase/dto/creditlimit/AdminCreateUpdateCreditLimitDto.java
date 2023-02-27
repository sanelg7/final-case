package com.definex.practicum.finalcase.dto.creditlimit;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateUpdateCreditLimitDto {

    @NonNull
    @Positive
    private Double amount;

}
