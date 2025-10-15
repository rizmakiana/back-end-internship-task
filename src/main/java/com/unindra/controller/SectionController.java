package com.unindra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.SectionRequest;
import com.unindra.model.response.SectionResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.SectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class SectionController {
    
    private final SectionService service;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "/staff/sections")
    public ResponseEntity<WebResponse<List<SectionResponse>>> getAll() {
        return ResponseEntity.ok(
            WebResponse.<List<SectionResponse>>builder()
                .data(service.getAll())
                .build()
        );
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "/staff/sections")
    public void add(@RequestBody SectionRequest request) {
        ResponseEntity.ok(
            WebResponse.<SectionResponse>builder()
            .data(service.add(request))
            .message("Kelas berhasil ditambah")
            .build()
        );
    }
}
