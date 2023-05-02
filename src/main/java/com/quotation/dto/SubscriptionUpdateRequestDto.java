package com.quotation.dto;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public final class SubscriptionUpdateRequestDto {
    @NotNull(message = "Quotation ID is required")
    private Long quotationId;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @NotNull(message = "Valid until date is required")
    @Future(message = "Valid until date must be in the future")
    private LocalDate validUntil;
}

