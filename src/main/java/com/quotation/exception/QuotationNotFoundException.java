package com.quotation.exception;

public final class QuotationNotFoundException extends RuntimeException {
    private final String message;

    private QuotationNotFoundException(String message) {
        this.message = message;
    }

    public static QuotationNotFoundException notFound() {
        return new QuotationNotFoundException("Quotation not found.");
    }

    public static QuotationNotFoundException withId(Long id) {
        return new QuotationNotFoundException("Quotation with ID " + id + " not found.");
    }

    @Override
    public String getMessage() {
        return message;
    }
}
