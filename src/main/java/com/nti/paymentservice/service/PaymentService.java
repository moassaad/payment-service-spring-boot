package com.nti.paymentservice.service;

import com.nti.paymentservice.dto.PaymentRequest;
import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.entity.PaymentStatus;
import com.nti.paymentservice.repository.PaymentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    public final PaymentRepository paymentRepository;
    private final NotificationService notificationService;

    public PaymentEntity createPayment(@RequestBody @Valid PaymentRequest paymentRequest, String apiKey) {

        boolean ex = paymentRepository.existsByCustomerIdAndOrderIdAndClientId(paymentRequest.getCustomerId(),
                paymentRequest.getOrderId(), apiKey);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setCustomerId(paymentRequest.getCustomerId());
        paymentEntity.setOrderId(paymentRequest.getOrderId());
        paymentEntity.setAmount(paymentRequest.getAmount());

        paymentEntity.setStatus(PaymentStatus.SUCCESS);
        paymentEntity.setProcessedAt(LocalDate.now());
        paymentEntity.setCreatedAt(LocalDate.now());
        paymentEntity.setClientId(apiKey);

        if (ex) {
            PaymentEntity payment = paymentRepository.findByCustomerIdAndOrderIdAndClientId(
                    paymentEntity.getCustomerId(),
                    paymentEntity.getOrderId(), apiKey);
            log.warn("payment alrady exists payment id={}.", payment.getPaymentId());
            return payment;
            // paymentEntity.setPaymentId(12345645L);
            // return paymentEntity;
        }

        PaymentEntity payment = paymentRepository.save(paymentEntity);

        String message = "Successful Payment, amount " + payment.getAmount() + "created at " +  payment.getCreatedAt();

        log.info(message);

        this.sendNotification(message, payment.getCustomerId());
//        notificationService.send(message, payment.getCustomerId());

        return payment;

    }
    @Retryable(
            value = { Exception.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    private void sendNotification(String message,Long customerId){
        notificationService.send(message, customerId);
    }
}
