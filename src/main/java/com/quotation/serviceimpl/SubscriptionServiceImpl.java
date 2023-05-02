package com.quotation.serviceimpl;

import com.quotation.dto.QuotationResponseDto;
import com.quotation.dto.SubscriptionRequestDto;
import com.quotation.dto.SubscriptionResponseDto;
import com.quotation.entity.SubscriptionEntity;
import com.quotation.exception.QuotationNotFoundException;
import com.quotation.exception.SubscriptionNotFoundException;
import com.quotation.repository.QuotationRepository;
import com.quotation.repository.SubscriptionRepository;
import com.quotation.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final QuotationRepository quotationRepository;
    private final ModelMapper modelMapper;

    @Override
    public SubscriptionResponseDto createSubscription(final SubscriptionRequestDto subscriptionRequest) {
        return quotationRepository.findById(subscriptionRequest.getQuotationId())
                .map(quotation -> {
                    final SubscriptionEntity subscription = modelMapper.map(subscriptionRequest, SubscriptionEntity.class);
                    subscription.setQuotation(quotation);

                    final SubscriptionEntity savedSubscription = subscriptionRepository.save(subscription);

                    final SubscriptionResponseDto subscriptionResponse = modelMapper.map(savedSubscription, SubscriptionResponseDto.class);
                    subscriptionResponse.setQuotation(modelMapper.map(savedSubscription.getQuotation(), QuotationResponseDto.class));

                    return subscriptionResponse;
                })
                .orElseThrow(() -> QuotationNotFoundException.withId(subscriptionRequest.getQuotationId()));
    }

    @Override
    public SubscriptionResponseDto getSubscription(final Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .map(subscription -> {
                    SubscriptionResponseDto subscriptionResponse = modelMapper.map(subscription, SubscriptionResponseDto.class);
                    subscriptionResponse.setQuotation(modelMapper.map(subscription.getQuotation(), QuotationResponseDto.class));
                    return subscriptionResponse;
                })
                .orElseThrow(SubscriptionNotFoundException::new);
    }

}

