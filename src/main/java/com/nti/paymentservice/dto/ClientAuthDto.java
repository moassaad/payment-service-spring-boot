package com.nti.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ClientAuthDto {
    private UUID apiKey;
}
