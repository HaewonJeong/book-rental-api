package com.ohgiraffers.bookrentalapi.Exceiption;

public class RentalNotFoundException extends RuntimeException{
    public RentalNotFoundException(String message) {
        super(message);
    }
}
