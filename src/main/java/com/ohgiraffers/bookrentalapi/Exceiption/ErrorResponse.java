package com.ohgiraffers.bookrentalapi.Exceiption;

import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorResponse {

    private final int status;
    private final String errorCode;
    private final String message;
    private final  Map<String, String> errors;

    public ErrorResponse(int status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errors = null;

    }
    public ErrorResponse(int status, String errorCode, String message, Map<String, String> errors) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

}
