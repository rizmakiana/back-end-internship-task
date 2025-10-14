package com.unindra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.response.ClassroomResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.ClassroomService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ClassroomController {

    private final ClassroomService service;

    @GetMapping(path = "staff/sections")
    public ResponseEntity<WebResponse<List<ClassroomResponse>>> getAll() {
        return ResponseEntity.ok(
                WebResponse.<List<ClassroomResponse>>builder()
                        .data(service.getAll())
                        .build());
    }

}
