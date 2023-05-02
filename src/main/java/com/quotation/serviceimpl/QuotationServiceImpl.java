package com.quotation.serviceimpl;

import com.quotation.dto.QuotationRequestDto;
import com.quotation.dto.QuotationResponseDto;
import com.quotation.entity.CustomerEntity;
import com.quotation.entity.QuotationEntity;
import com.quotation.exception.CustomerNotFoundException;
import com.quotation.repository.CustomerRepository;
import com.quotation.repository.QuotationRepository;
import com.quotation.service.QuotationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuotationServiceImpl implements QuotationService {
    private final QuotationRepository quotationRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public QuotationResponseDto createQuotation(QuotationRequestDto quotationRequest) {
        final CustomerEntity customer = customerRepository.findById(quotationRequest.getCustomerId())
                .orElseThrow(CustomerNotFoundException.withId(quotationRequest.getCustomerId()));

        final QuotationEntity quotation = modelMapper.map(quotationRequest, QuotationEntity.class);
        quotation.setCustomer(customer);

        final QuotationEntity savedQuotation = quotationRepository.save(quotation);

        return modelMapper.map(savedQuotation, QuotationResponseDto.class);
    }

}

