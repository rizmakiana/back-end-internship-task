package com.unindra.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.StudentReport;
import com.unindra.model.request.ClassroomReport;
import com.unindra.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ReportController {

    private final ReportService service;

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "/report/students") 
    public ResponseEntity<byte[]> studentReport(@RequestBody List<StudentReport> students) {
        // 1. Panggil service untuk generate PDF via Typst
        byte[] pdfBytes = service.generateStudentPdf(students);

        // 2. Bungkus dalam ResponseEntity dengan Header PDF
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                // 'attachment' supaya browser/client tau ini file untuk di-download
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"laporan_siswa.pdf\"")
                .body(pdfBytes);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(path = "/report/classrooms") 
    public ResponseEntity<byte[]> classroomReport(@RequestBody List<ClassroomReport> students) {
        // 1. Panggil service untuk generate PDF via Typst
        byte[] pdfBytes = service.generateClassroomPdf(students);

        // 2. Bungkus dalam ResponseEntity dengan Header PDF
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                // 'attachment' supaya browser/client tau ini file untuk di-download
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"laporan_siswa.pdf\"")
                .body(pdfBytes);
    }
}