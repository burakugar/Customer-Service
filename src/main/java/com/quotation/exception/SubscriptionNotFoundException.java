package com.quotation.exception;

public final class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException() {
        super("Subscription not found");
    }
}

