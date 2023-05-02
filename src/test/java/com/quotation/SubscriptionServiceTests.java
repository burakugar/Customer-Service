package com.quotation;

import com.quotation.dto.QuotationResponseDto;
import com.quotation.dto.SubscriptionRequestDto;
import com.quotation.dto.SubscriptionResponseDto;
import com.quotation.entity.CustomerEntity;
import com.quotation.entity.QuotationEntity;
import com.quotation.entity.SubscriptionEntity;
import com.quotation.repository.QuotationRepository;
import com.quotation.repository.SubscriptionRepository;
import com.quotation.serviceimpl.SubscriptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SubscriptionServiceTests {
    @Test
    void testCreateSubscription() {
        QuotationEntity quotation = new QuotationEntity();
        quotation.setId(1L);
        quotation.setBeginningOfInsurance(LocalDate.of(2023, 5, 2));
        quotation.setInsuredAmount(BigDecimal.valueOf(100000.00));
        quotation.setDateOfSigningMortgage(LocalDate.of(2023, 5, 1));
        SubscriptionRequestDto subscriptionRequest = new SubscriptionRequestDto();
        subscriptionRequest.setQuotationId(1L);
        subscriptionRequest.setStartDate(LocalDate.of(2023, 5, 2));
        subscriptionRequest.setValidUntil(LocalDate.of(2024, 5, 1));
        QuotationRepository quotationRepository = mock(QuotationRepository.class);
        when(quotationRepository.findById(1L)).thenReturn(Optional.of(quotation));
        SubscriptionRepository subscriptionRepository = mock(SubscriptionRepository.class);
        when(subscriptionRepository.save(any(SubscriptionEntity.class)))
                .thenAnswer(invocationOnMock -> {
                    SubscriptionEntity savedSubscription = invocationOnMock.getArgument(0);
                    savedSubscription.setId(1L);
                    return savedSubscription;
                });
        ModelMapper modelMapper = new ModelMapper();
        SubscriptionServiceImpl subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, quotationRepository, modelMapper);
        SubscriptionResponseDto actualSubscriptionResponseDto = subscriptionService.createSubscription(subscriptionRequest);
        verify(quotationRepository).findById(1L);
        verify(subscriptionRepository).save(argThat(subscriptionEntity -> subscriptionEntity.getQuotation().equals(quotation) &&
                subscriptionEntity.getStartDate().equals(subscriptionRequest.getStartDate()) &&
                subscriptionEntity.getValidUntil().equals(subscriptionRequest.getValidUntil())));

        SubscriptionResponseDto expectedSubscriptionResponseDto = new SubscriptionResponseDto();
        expectedSubscriptionResponseDto.setId(1L);
        expectedSubscriptionResponseDto.setStartDate(LocalDate.of(2023, 5, 2));
        expectedSubscriptionResponseDto.setValidUntil(LocalDate.of(2024, 5, 1));
        expectedSubscriptionResponseDto.setQuotation(modelMapper.map(quotation, QuotationResponseDto.class));
        assertEquals(expectedSubscriptionResponseDto, actualSubscriptionResponseDto);
        verifyNoMoreInteractions(quotationRepository, subscriptionRepository);
    }
    @Test
    void testGetSubscription() {
        QuotationEntity quotation = new QuotationEntity();
        quotation.setId(1L);
        quotation.setBeginningOfInsurance(LocalDate.of(2023, 5, 2));
        quotation.setInsuredAmount(BigDecimal.valueOf(100000.00));
        quotation.setDateOfSigningMortgage(LocalDate.of(2023, 5, 1));
        quotation.setCustomer(new CustomerEntity());

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setId(1L);
        subscription.setQuotation(quotation);
        subscription.setStartDate(LocalDate.of(2023, 5, 3));
        subscription.setValidUntil(LocalDate.of(2024, 5, 3));

        SubscriptionRepository subscriptionRepository = mock(SubscriptionRepository.class);
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));
        ModelMapper modelMapper = new ModelMapper();
        SubscriptionServiceImpl subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, null, modelMapper);
        SubscriptionResponseDto actualSubscriptionResponseDto = subscriptionService.getSubscription(1L);
        SubscriptionResponseDto expectedSubscriptionResponseDto = new SubscriptionResponseDto();
        expectedSubscriptionResponseDto.setId(1L);
        expectedSubscriptionResponseDto.setQuotation(modelMapper.map(quotation, QuotationResponseDto.class));
        expectedSubscriptionResponseDto.setStartDate(LocalDate.of(2023, 5, 3));
        expectedSubscriptionResponseDto.setValidUntil(LocalDate.of(2024, 5, 3));
        assertAll("subscriptionResponseDto",
                () -> assertEquals(expectedSubscriptionResponseDto.getId(), actualSubscriptionResponseDto.getId()),
                () -> assertEquals(expectedSubscriptionResponseDto.getQuotation(), actualSubscriptionResponseDto.getQuotation()),
                () -> assertEquals(expectedSubscriptionResponseDto.getStartDate(), actualSubscriptionResponseDto.getStartDate()),
                () -> assertEquals(expectedSubscriptionResponseDto.getValidUntil(), actualSubscriptionResponseDto.getValidUntil())
        );
        verify(subscriptionRepository).findById(1L);
        verifyNoMoreInteractions(subscriptionRepository);
    }

}
