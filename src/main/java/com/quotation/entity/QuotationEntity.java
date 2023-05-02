package com.quotation.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "quotation")
@Builder
public class QuotationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate beginningOfInsurance;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal insuredAmount;

    @NotNull
    private LocalDate dateOfSigningMortgage;

    @ManyToOne
    @NotNull
    private CustomerEntity customer;
}
