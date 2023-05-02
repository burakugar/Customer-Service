package com.quotation;

import com.quotation.dto.QuotationRequestDto;
import com.quotation.dto.QuotationResponseDto;
import com.quotation.entity.CustomerEntity;
import com.quotation.entity.QuotationEntity;
import com.quotation.repository.CustomerRepository;
import com.quotation.repository.QuotationRepository;
import com.quotation.serviceimpl.QuotationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class QuotationServiceTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldCreateQuotation() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("Burak");
        customer.setLastName("Ugar");
        customer.setMiddleName("Werner");
        customer.setEmail("email@gmail.com");
        customer.setPhoneNumber("+420745835");
        customer.setBirthDate(LocalDate.of(1990, 1, 1));

        QuotationRequestDto quotationRequest = new QuotationRequestDto();
        quotationRequest.setBeginningOfInsurance(LocalDate.of(2023, 5, 2));
        quotationRequest.setInsuredAmount(BigDecimal.valueOf(100000.00));
        quotationRequest.setDateOfSigningMortgage(LocalDate.of(2023, 5, 1));
        quotationRequest.setCustomerId(1L);

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        QuotationRepository quotationRepository = mock(QuotationRepository.class);
        when(quotationRepository.save(any(QuotationEntity.class)))
                .thenAnswer(invocationOnMock -> {
                    QuotationEntity savedQuotation = invocationOnMock.getArgument(0);
                    savedQuotation.setId(1L);
                    return savedQuotation;
                });

        ModelMapper modelMapper = new ModelMapper();

        QuotationServiceImpl quotationService = new QuotationServiceImpl(quotationRepository, customerRepository, modelMapper);

        QuotationResponseDto actualQuotationResponseDto = quotationService.createQuotation(quotationRequest);

        verify(customerRepository).findById(1L);

        verify(quotationRepository).save(argThat(quotationEntity -> quotationEntity.getCustomer().equals(customer) &&
                quotationEntity.getBeginningOfInsurance().equals(quotationRequest.getBeginningOfInsurance()) &&
                quotationEntity.getInsuredAmount().equals(quotationRequest.getInsuredAmount()) &&
                quotationEntity.getDateOfSigningMortgage().equals(quotationRequest.getDateOfSigningMortgage())));

        QuotationResponseDto expectedQuotationResponseDto = new QuotationResponseDto();
        expectedQuotationResponseDto.setId(1L);
        expectedQuotationResponseDto.setBeginningOfInsurance(LocalDate.of(2023, 5, 2));
        expectedQuotationResponseDto.setInsuredAmount(BigDecimal.valueOf(100000.00));
        expectedQuotationResponseDto.setDateOfSigningMortgage(LocalDate.of(2023, 5, 1));

        assertAll("quotationResponseDto",
                () -> assertEquals(expectedQuotationResponseDto.getId(), actualQuotationResponseDto.getId()),
                () -> assertEquals(expectedQuotationResponseDto.getBeginningOfInsurance(), actualQuotationResponseDto.getBeginningOfInsurance()),
                () -> assertEquals(expectedQuotationResponseDto.getInsuredAmount(), actualQuotationResponseDto.getInsuredAmount()),
                () -> assertEquals(expectedQuotationResponseDto.getDateOfSigningMortgage(), actualQuotationResponseDto.getDateOfSigningMortgage())
        );

        assertNotNull(actualQuotationResponseDto.getId());

        verifyNoMoreInteractions(customerRepository, quotationRepository);
    }

}
