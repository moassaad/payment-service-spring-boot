package com.nti.paymentservice.exception;

import com.nti.paymentservice.dto.FailedValidationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleGlobalException(MethodArgumentNotValidException ex){
        List<?> errors = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            FailedValidationDto failedValidationDto = new FailedValidationDto();
            failedValidationDto.setFiled(fieldError.getField());
            failedValidationDto.setMessage(fieldError.getDefaultMessage());
            return failedValidationDto;
        }).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<?> handleClientAlreadyExistsException(ClientAlreadyExistsException ex){

        Map<String,String> errors = new HashMap<>();

        errors.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);

    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<?> handleClientNotFoundException(ClientNotFoundException ex){

        Map<String,String> errors = new HashMap<>();

        errors.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);

    }
    @ExceptionHandler(PaymentStatusException.class)
    public ResponseEntity<?> handlePaymentStatusException(PaymentStatusException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePaymentNotFound(PaymentNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("error", "Payment Not Found");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handlePaymentNotFound(HttpServerErrorException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


