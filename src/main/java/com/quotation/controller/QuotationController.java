package com.quotation.controller;

import com.quotation.dto.QuotationRequestDto;
import com.quotation.dto.QuotationResponseDto;
import com.quotation.service.QuotationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/quotations")
@AllArgsConstructor
public class QuotationController {
    private final QuotationService quotationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuotationResponseDto createQuotation(@Valid @RequestBody QuotationRequestDto quotationRequest) {
        return quotationService.createQuotation(quotationRequest);
    }
}

