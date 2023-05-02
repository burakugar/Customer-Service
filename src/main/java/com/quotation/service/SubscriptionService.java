package com.quotation.service;

import com.quotation.dto.SubscriptionRequestDto;
import com.quotation.dto.SubscriptionResponseDto;

public interface SubscriptionService {
    SubscriptionResponseDto createSubscription(SubscriptionRequestDto subscriptionRequest);
    SubscriptionResponseDto getSubscription(Long subscriptionId);
}
