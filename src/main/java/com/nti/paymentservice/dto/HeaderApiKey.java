package com.nti.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeaderApiKey {
    private String apikey;


    @Override
    public String toString() {
        return "HeaderApiKey{" +
                "apikey='" + apikey + '\'' +
                '}';
    }
}
