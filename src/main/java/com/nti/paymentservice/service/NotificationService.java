package com.nti.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nti.paymentservice.dto.NotificationRequest;
import com.nti.paymentservice.entity.NotificationStatus;
import com.nti.paymentservice.entity.NotificationChannel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${service.notification-service.url}")
    private String url;
    @Value("${service.notification-service.secret-key}")
    private String key;


    public void send(String message, NotificationStatus notificationStatus, Long userId, NotificationChannel channel) {

            NotificationRequest notificationRequest = new NotificationRequest();
            notificationRequest.setMessage(message);
            notificationRequest.setChannel(channel);
            notificationRequest.setUserId(userId);
            notificationRequest.setType(notificationStatus.getName());

            try {

                String jsonPayload = objectMapper.writeValueAsString(notificationRequest);

                log.info("sending notification to payment service with json payload={}", jsonPayload);

                HttpHeaders headers = new HttpHeaders();
                headers.set("X-API-KEY", key);
                headers.set("Content-Type", "application/json");

                HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

                ResponseEntity<String> response = restTemplate.exchange(url + "/notification", HttpMethod.POST, entity, String.class);
                log.info("notification sent");

            } catch (JsonProcessingException | HttpServerErrorException exception){
                log.error("Error while sending notification: message={}", exception.getMessage());
            }

    }
    public void send(String message, Long userId, NotificationStatus notificationStatus) {
        this.send(message, notificationStatus, userId, NotificationChannel.EMAIL);
    }
    public void send(String message, Long userId) {
        send(message, userId, NotificationStatus.PAYMENT_SUCCESS);
    }

}
