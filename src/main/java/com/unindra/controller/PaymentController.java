package com.unindra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.response.WebResponse;
import com.unindra.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class PaymentController {

    private final PaymentService service;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "payment/referenceNumber")
    public void getReferenceNumber() {
        ResponseEntity.ok(
                WebResponse.<String>builder()
                        .data(service.generateReferenceNumber())
                        .build());
    }
}
