package com.nti.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
    private String status;
    private String message;
    private Data data;

    @Getter
    @Setter
    public static class Data {
        private int notificationId;
        private int userId;
        private String sentAt;
        private String statusMessage;
    }
}