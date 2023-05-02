package com.quotation.serviceimpl;

import com.quotation.dto.CustomerRequestDto;
import com.quotation.dto.CustomerResponseDto;
import com.quotation.entity.CustomerEntity;
import com.quotation.exception.CustomerNotFoundException;
import com.quotation.repository.CustomerRepository;
import com.quotation.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomerResponseDto createCustomer(final CustomerRequestDto customerRequest) {
        final CustomerEntity customer = modelMapper.map(customerRequest, CustomerEntity.class);
        customerRepository.save(customer);
        return modelMapper.map(customer, CustomerResponseDto.class);
    }

    @Override
    public CustomerResponseDto updateCustomer(final Long customerId, final CustomerRequestDto customerRequest) {
        final CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException.withId(customerId));
        modelMapper.map(customerRequest, customer);
        customerRepository.save(customer);
        return modelMapper.map(customer, CustomerResponseDto.class);
    }

    @Override
    public void deleteCustomer(final Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerResponseDto getCustomer(final Long customerId) {
        final CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException.withId(customerId));

        return modelMapper.map(customer, CustomerResponseDto.class);
    }


}
