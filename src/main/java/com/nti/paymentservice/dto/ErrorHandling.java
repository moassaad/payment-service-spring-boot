package com.nti.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorHandling {
    private String message;


    @Override
    public String toString() {
        return "ErrorHandeling{" +
                "message='" + message + '\'' +
                '}';
    }
}