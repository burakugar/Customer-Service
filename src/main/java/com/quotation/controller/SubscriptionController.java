package com.quotation.controller;

import com.quotation.dto.SubscriptionRequestDto;
import com.quotation.dto.SubscriptionResponseDto;
import com.quotation.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@AllArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDto> createSubscription(@RequestBody SubscriptionRequestDto subscriptionRequest) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.createSubscription(subscriptionRequest);
        return new ResponseEntity<>(subscriptionResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionResponseDto> getSubscription(@PathVariable Long subscriptionId) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.getSubscription(subscriptionId);
        return new ResponseEntity<>(subscriptionResponseDto, HttpStatus.OK);
    }
}

