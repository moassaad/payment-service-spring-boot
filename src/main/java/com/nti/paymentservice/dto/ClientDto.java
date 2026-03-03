package com.nti.paymentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ClientDto {
    @NotBlank
    @Length(max = 100)
    private String username;
    @NotNull
    private String password;
}
