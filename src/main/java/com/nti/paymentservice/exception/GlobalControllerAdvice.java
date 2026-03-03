package com.nti.paymentservice.exception;

import com.nti.paymentservice.dto.ErrorHandling;
import com.nti.paymentservice.dto.ErrorsHandling;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception){
        ErrorsHandling errors = new ErrorsHandling();
        List<ErrorsHandling.ErrorDetail> ERRORS = exception.getBindingResult().getFieldErrors().stream().map(
                error -> {
                    ErrorsHandling.ErrorDetail errorDetail = new ErrorsHandling.ErrorDetail();
                    errorDetail.setField(error.getField());
                    errorDetail.setMessage(error.getDefaultMessage());
                    return errorDetail;
                }
        ).toList();
        errors.setErrors(ERRORS);
        return ResponseEntity.badRequest().body(ERRORS);
    }
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleMissingHeader(MissingRequestHeaderException exception){
        ErrorHandling error = new ErrorHandling();
        error.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<?> handleMissingHeader(PaymentNotFoundException exception){
        ErrorHandling error = new ErrorHandling();
        error.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(PaymentRefundException.class)
    public ResponseEntity<?> handleMissingHeader(PaymentRefundException exception){
        ErrorHandling error = new ErrorHandling();
        error.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

}
