package com.quotation.service;

import com.quotation.dto.CustomerRequestDto;
import com.quotation.dto.CustomerResponseDto;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto customerRequest);

    CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto customerRequest);

    void deleteCustomer(Long customerId);

    CustomerResponseDto getCustomer(Long customerId);
}

