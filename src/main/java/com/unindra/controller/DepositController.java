package com.unindra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.DepositRequest;
import com.unindra.model.response.StudentDepositResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.DepositService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class DepositController {

    private final DepositService service;

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "/staff/deposits/{studentId}")
    public ResponseEntity<WebResponse<String>> deposit(@PathVariable String studentId, @RequestBody DepositRequest request) {

        service.deposit(studentId, request);
        return ResponseEntity.ok(
            WebResponse.<String>builder()
                .message("Tabungan berhasil ditambah")
                .build()
        );
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "/staff/deposits")
    public ResponseEntity<WebResponse<List<StudentDepositResponse>>> getStudentDeposits() {
        return ResponseEntity.ok(
                WebResponse.<List<StudentDepositResponse>>builder()
                        .data(service.getStudentsDeposit())
                        .build()
        );
    }
}
