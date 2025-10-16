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

import com.unindra.model.request.SectionRequest;
import com.unindra.model.request.SectionUpdateRequest;
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
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "/staff/sections")
    public ResponseEntity<WebResponse<SectionResponse>> add(@RequestBody SectionRequest request) {
        return ResponseEntity.ok(
                WebResponse.<SectionResponse>builder()
                        .data(service.add(request))
                        .message("Kelas berhasil ditambah")
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping(path = "/staff/sections/{code}")
    public ResponseEntity<WebResponse<SectionResponse>> update(@PathVariable String code,
            @RequestBody SectionUpdateRequest request) {
        return ResponseEntity.ok(
                WebResponse.<SectionResponse>builder()
                        .data(service.update(code, request))
                        .message("Kelas berhasil ditambah")
                        .build());
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping(path = "/staff/sections/{code}")
    public ResponseEntity<WebResponse<SectionResponse>> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.ok(
                WebResponse.<SectionResponse>builder()
                        .message("Kelas berhasil dihapus")
                        .build());

    }
}
