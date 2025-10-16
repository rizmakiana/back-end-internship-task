package com.unindra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.ClassroomRequest;
import com.unindra.model.response.ClassroomResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.ClassroomService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ClassroomController {

    private final ClassroomService service;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(path = "staff/classrooms")
    public ResponseEntity<WebResponse<List<ClassroomResponse>>> getAll() {
        return ResponseEntity.ok(
                WebResponse.<List<ClassroomResponse>>builder()
                        .data(service.getAll())
                        .build());
    }


    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "staff/classrooms")
    public ResponseEntity<WebResponse<ClassroomResponse>> add(@RequestBody ClassroomRequest request) {
        return ResponseEntity.ok(
                WebResponse.<ClassroomResponse>builder()
                        .data(service.add(request))
                        .message("Tingkat kelas berhasil ditambah")
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "staff/classrooms/{code}")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.ok(
                WebResponse.<String>builder()
                        .message("Tingkat kelas berhasil dihapus")
                        .build());
    }

}
