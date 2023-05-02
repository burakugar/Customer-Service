package com.quotation.dto;

import lombok.Getter;

import java.util.List;
@Getter
public final class ErrorDto {
    private final int status;
    private final String message;
    private final List<String> details;

    public ErrorDto(int status, String message, List<String> details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }
    public ErrorDto(int status, String message) {
        this(status, message, null);
    }

}
