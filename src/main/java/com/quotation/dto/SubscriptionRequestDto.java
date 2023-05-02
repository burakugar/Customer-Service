package com.quotation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public final class SubscriptionRequestDto {
    @NotNull
    private Long quotationId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date can not be null")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Valid until can not be null")
    private LocalDate validUntil;
}