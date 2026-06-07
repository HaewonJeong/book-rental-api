package com.ohgiraffers.bookrentalapi.Exceiption;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message){ super(message); }
}
