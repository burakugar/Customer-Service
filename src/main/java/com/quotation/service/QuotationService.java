package com.quotation.service;

import com.quotation.dto.QuotationRequestDto;
import com.quotation.dto.QuotationResponseDto;

public interface QuotationService {
    QuotationResponseDto createQuotation(QuotationRequestDto quotationRequest);
}
