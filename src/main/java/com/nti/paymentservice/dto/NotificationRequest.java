package com.nti.paymentservice.dto;

import com.nti.paymentservice.entity.NotificationChannel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    private Long userId;

    private String type;

    private NotificationChannel channel;

    private String message;

}
