package com.quotation.advice;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.quotation.dto.ErrorDto;
import com.quotation.exception.CustomerNotFoundException;
import com.quotation.exception.QuotationNotFoundException;
import com.quotation.exception.SubscriptionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;

@RestControllerAdvice
public final class BaseControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Validation failed", errorMessages);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Validation failed", errorMessages);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.value(), "Customer not found", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(QuotationNotFoundException.class)
    public ResponseEntity<ErrorDto> handleQuotationNotFoundException(QuotationNotFoundException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.value(), "Quotation not found", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorDto> handleSubscriptionNotFoundException(SubscriptionNotFoundException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.value(), "Subscription not found", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
