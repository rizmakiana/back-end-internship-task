package com.unindra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.DepositRequest;
import com.unindra.model.response.StudentDepositResponse;
import com.unindra.model.response.StudentDepositsHistory;
import com.unindra.model.response.WebResponse;
import com.unindra.service.DepositService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class DepositController {

	private final DepositService service;

	@PreAuthorize("hasRole('STAFF')")
	@PostMapping(path = "/staff/deposits/{studentId}/deposit")
	public ResponseEntity<WebResponse<StudentDepositResponse>> deposit(
			@PathVariable String studentId,
			@RequestBody DepositRequest request) {

		return ResponseEntity.ok(
				WebResponse.<StudentDepositResponse>builder()
						.data(service.deposit(studentId, request))
						.message("Tabungan berhasil ditambah")
						.build());
	}

	@PreAuthorize("hasRole('STAFF')")
	@PostMapping(path = "/staff/deposits/{studentId}/withdraw")
	public ResponseEntity<WebResponse<StudentDepositResponse>> withdrawal(
			@PathVariable String studentId,
			@RequestBody DepositRequest request) {

		return ResponseEntity.ok(
				WebResponse.<StudentDepositResponse>builder()
						.data(service.withdrawal(studentId, request))
						.message("Tabungan berhasil ditarik")
						.build());
	}

	@PreAuthorize("hasRole('STAFF')")
	@GetMapping(path = "/staff/deposits/{studentId}")
	public ResponseEntity<WebResponse<List<StudentDepositsHistory>>> getAllStudentHistoryDeposits(
			@PathVariable String studentId) {

		return ResponseEntity.ok(
				WebResponse.<List<StudentDepositsHistory>>builder()
						.data(service.getStudentDepositHistory(studentId))
						.build());
	}

	@PreAuthorize("hasRole('STAFF')")
	@GetMapping(path = "/staff/deposits")
	public ResponseEntity<WebResponse<List<StudentDepositResponse>>> getAllStudentDeposits() {
		return ResponseEntity.ok(
				WebResponse.<List<StudentDepositResponse>>builder()
						.data(service.getStudentsDeposit())
						.build());
	}

	@PreAuthorize("hasRole('STAFF')")
	@GetMapping("/staff/deposits/reference-number")
	public ResponseEntity<WebResponse<String>> getReferenceNumber(
			@RequestParam("type") String type) {

		return ResponseEntity.ok(
				WebResponse.<String>builder()
						.data(service.generateReferenceNumber(type))
						.build());
	}

}
