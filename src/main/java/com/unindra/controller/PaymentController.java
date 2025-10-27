package com.unindra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.response.PaymentHistoryResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class PaymentController {

    private final PaymentService service;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "/payments/referenceNumber")
    public ResponseEntity<WebResponse<String>> getReferenceNumber() {
        return ResponseEntity.ok(
                WebResponse.<String>builder()
                        .data(service.generateReferenceNumber())
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "/payments")
    public ResponseEntity<WebResponse<List<PaymentHistoryResponse>>> getHistory() {
        return ResponseEntity.ok(
                WebResponse.<List<PaymentHistoryResponse>>builder()
                        .data(service.getAll())
                        .build());
    }
    
}
