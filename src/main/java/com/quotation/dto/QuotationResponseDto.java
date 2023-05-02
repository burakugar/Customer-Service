package com.quotation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public final class QuotationResponseDto {
    private Long id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginningOfInsurance;

    @NotNull
    private BigDecimal insuredAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfSigningMortgage;

    private CustomerResponseDto customer;
}
