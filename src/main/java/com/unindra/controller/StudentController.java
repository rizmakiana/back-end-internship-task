package com.unindra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.StudentRequest;
import com.unindra.model.request.StudentUpdate;
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

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "/staff/students")
    public ResponseEntity<WebResponse<StudentResponse>> add(@RequestBody StudentRequest request) {
        return ResponseEntity.ok(
                WebResponse.<StudentResponse>builder()
                        .data(service.add(request))
                        .message("Registrasi siswa sukses")
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping(path = "/staff/students/{studentId}")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(
                WebResponse.<String>builder()
                        .message("Siswa berhasil dihapus")
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping(path = "/staff/students/{studentId}")
    public ResponseEntity<WebResponse<StudentResponse>> update(@PathVariable String id, @RequestBody StudentUpdate update) {
        return ResponseEntity.ok(
                WebResponse.<StudentResponse>builder()
                        .data(service.update(id, update))
                        .message("Siswa berhasil diedit")
                        .build());
    }

}
