package com.nti.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ErrorsHandling {
    private List<ErrorDetail> errors;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetail {
        private String field;
        private String message;

        @Override
        public String toString() {
            return "ErrorDetail{" +
                    "field='" + field + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ErrorsHandeling{" +
                "errors=" + errors +
                '}';
    }
}