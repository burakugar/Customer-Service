package com.quotation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public final class QuotationRequestDto {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginningOfInsurance;

    @NotNull
    @Positive
    private BigDecimal insuredAmount;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfSigningMortgage;

    @NotNull
    private Long customerId;
}

