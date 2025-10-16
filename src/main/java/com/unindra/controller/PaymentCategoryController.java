package com.unindra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.PaymentCategoryRequest;
import com.unindra.model.response.PaymentCategoryResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.PaymentCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class PaymentCategoryController {

    private final PaymentCategoryService service;

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "/staff/payment-categories")
    public ResponseEntity<WebResponse<PaymentCategoryResponse>> add(@RequestBody PaymentCategoryRequest request) {
        return ResponseEntity.ok(
                WebResponse.<PaymentCategoryResponse>builder()
                        .data(service.add(request))
                        .message("Kategori Pembayaran berhasil ditambah")
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "/staff/payment-categories")
    public ResponseEntity<WebResponse<List<PaymentCategoryResponse>>> get() {
        return ResponseEntity.ok(
                WebResponse.<List<PaymentCategoryResponse>>builder()
                        .data(service.getAll())
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping(path = "/staff/payment-categories/{name}")
    public ResponseEntity<WebResponse<PaymentCategoryResponse>> update(@PathVariable String name, @RequestBody PaymentCategoryRequest request) {
        return ResponseEntity.ok(
            WebResponse.<PaymentCategoryResponse>builder()
            .data(service.update(name, request))
            .message("Pembayaran berhasil diedit")
            .build()
        );
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping(path = "/staff/payment-categories/{name}")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable String name) {

        service.delete(name);
        return ResponseEntity.ok(
            WebResponse.<String>builder()
            .message("Pembayaran berhasil dihapus")
            .build()
        );
    }
}
