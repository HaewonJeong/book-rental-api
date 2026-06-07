package com.ohgiraffers.bookrentalapi.DTO;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMessage {

    private Integer status;
    private String message;
    private Map<String, Object> results;

}
