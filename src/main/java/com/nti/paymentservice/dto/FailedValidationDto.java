package com.nti.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FailedValidationDto {
    private String filed;
    private String message;
}
