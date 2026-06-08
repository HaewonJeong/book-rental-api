package com.ohgiraffers.bookrentalapi.Exceiption;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class LibraryExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e){

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        ErrorResponse response =
                new ErrorResponse(
                        400,
                        "VALIDATION_FAILED",
                        "요청 값 검증에 실패했습니다.",
                        errors
                );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerMemberNotFound(
      MemberNotFoundException exception,
      HttpServletRequest request){
          ErrorResponse response = new ErrorResponse(
                  HttpStatus.NOT_FOUND.value(),
                  HttpStatus.NOT_FOUND.name(),
                  exception.getMessage()
          );
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerMemberNotFound(
            BookNotFoundException exception,
            HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerMemberNotFound(
            RentalNotFoundException exception,
            HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND .value(),
                HttpStatus.NOT_FOUND .name(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND ).body(response);
    }

    @ExceptionHandler(BookAlreadyRentedException.class)
    public ResponseEntity<ErrorResponse> handlerMemberNotFound(
            BookAlreadyRentedException exception,
            HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST ).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handlerMemberNotFound(
            IllegalArgumentException exception,
            HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
