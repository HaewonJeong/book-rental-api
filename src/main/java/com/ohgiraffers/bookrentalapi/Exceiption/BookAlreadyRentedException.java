package com.ohgiraffers.bookrentalapi.Exceiption;

public class BookAlreadyRentedException extends RuntimeException{
    public BookAlreadyRentedException(String message){ super(message); }
}
