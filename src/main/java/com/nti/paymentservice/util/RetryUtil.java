package com.nti.paymentservice.util;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class RetryUtil {

    public static <T> T retry(Supplier<T> operation, int maxAttempts, long delayMillis) {
        int attempt = 0;

        while (true) {
            try {
                attempt++;
                return operation.get();
            } catch (Exception ex) {

                if (attempt >= maxAttempts) {
                    throw ex;
                }

                log.warn("Retry attempt {} failed, Retrying...", attempt);
//                System.out.println("Retry attempt " + attempt + " failed. Retrying...");

                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", e);
                }
            }
        }
    }
}
