package com.quotation.controller;

import com.quotation.dto.CustomerRequestDto;
import com.quotation.dto.CustomerResponseDto;
import com.quotation.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto createCustomer(@Valid @RequestBody CustomerRequestDto customerRequest) {
        return customerService.createCustomer(customerRequest);
    }

    @GetMapping("/{customerId}")
    public CustomerResponseDto getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @PutMapping("/{customerId}")
    public CustomerResponseDto updateCustomer(@PathVariable Long customerId, @Valid @RequestBody CustomerRequestDto customerRequest) {
        return customerService.updateCustomer(customerId, customerRequest);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }
}

