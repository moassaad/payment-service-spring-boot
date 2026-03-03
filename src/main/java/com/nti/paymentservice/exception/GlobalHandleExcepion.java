package com.nti.paymentservice.exception;

import com.nti.paymentservice.dto.FailedValidationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleExcepion {

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

}
