package com.unindra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.response.StudentResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class StudentController {

    private final StudentService service;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "/staff/students")
    public ResponseEntity<WebResponse<List<StudentResponse>>> getAll() {
        return ResponseEntity.ok(
                WebResponse.<List<StudentResponse>>builder()
                        .data(service.getAll())
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "/staff/students/{studentId}")
    public ResponseEntity<WebResponse<StudentResponse>> getById(@PathVariable String studentId) {
        return ResponseEntity.ok(
                WebResponse.<StudentResponse>builder()
                        .data(service.getByStudentId(studentId))
                        .build());
    }


}
