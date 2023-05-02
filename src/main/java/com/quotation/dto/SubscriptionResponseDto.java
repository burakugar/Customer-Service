package com.quotation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class SubscriptionResponseDto {
    private Long id;
    private QuotationResponseDto quotation;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validUntil;
}



