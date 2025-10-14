package com.unindra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.DepartmentRequest;
import com.unindra.model.response.DepartmentResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.DepartmentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class DepartmentController {

    private final DepartmentService service;

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "/staff/departments")
    public ResponseEntity<WebResponse<DepartmentResponse>> add(@RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(
                WebResponse.<DepartmentResponse>builder()
                        .data(service.add(request))
                        .message(String.format("Jurusan %s berhasil ditambahkan", request.getDepartmentName()))
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "/staff/departments")
    public ResponseEntity<WebResponse<List<DepartmentResponse>>> getAll() {
        return ResponseEntity.ok(
                WebResponse.<List<DepartmentResponse>>builder()
                        .data(service.getAll())
                        .build());
    }
    

}
