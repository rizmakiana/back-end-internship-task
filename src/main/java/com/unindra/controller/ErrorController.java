package com.unindra.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.model.response.WebResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<Object>> constraintViolationException(ConstraintViolationException exception) {

        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> error : exception.getConstraintViolations()) {
            String fieldName = error.getPropertyPath().toString();
            String message = error.getMessage();

            errors.put(fieldName, message);
        }

        WebResponse<Object> response = WebResponse.builder()
                .message("Validasi gagal")
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<Object>> apiException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(WebResponse.<Object>builder().errors(exception.getReason()).build());
    }

    @ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class })
    public ResponseEntity<WebResponse<Object>> handleAuthExceptions(Exception ex) {

        WebResponse<Object> response = WebResponse.builder()
                .errors("Nama pengguna atau Kata sandi salah")
                .build();

        return ResponseEntity.badRequest().body(response);
    }

}