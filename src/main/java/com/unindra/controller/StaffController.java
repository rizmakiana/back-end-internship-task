package com.unindra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.StaffRequest;
import com.unindra.model.response.WebResponse;
import com.unindra.service.StaffService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class StaffController {
    
    private final StaffService service;

    @PostMapping(path = "/staff")
    public ResponseEntity<WebResponse<String>> registration(@RequestBody StaffRequest request) {

        service.registration(request);
        return ResponseEntity.ok(
            WebResponse.<String>builder()
                .message("Registrasi Staff berhasil")
                .build()
        );
    }
}
