package com.definex.practicum.finalcase.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreditScoreDto {

    @NotBlank
    private Long id;

    @NonNull
    private Double creditScoreValue;
}
