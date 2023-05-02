package com.quotation.exception;

import java.util.function.Supplier;

public final class CustomerNotFoundException extends RuntimeException {
    private final String message;

    public CustomerNotFoundException(String message) {
        this.message = message;
    }

    public static Supplier<CustomerNotFoundException> notFound() {
        return () -> new CustomerNotFoundException("Customer not found.");
    }

    public static Supplier<CustomerNotFoundException> withId(final Long id) {
        return () -> new CustomerNotFoundException("Customer with ID " + id + " not found.");
    }


    @Override
    public String getMessage() {
        return message;
    }
}



