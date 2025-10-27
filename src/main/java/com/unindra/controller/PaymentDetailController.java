package com.unindra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.request.PaymentDetailRequest;
import com.unindra.model.request.PaymentDetailUpdate;
import com.unindra.model.response.PaymentDetailBillResponse;
import com.unindra.model.response.PaymentDetailResponse;
import com.unindra.model.response.StudentUnpaidResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.PaymentDetailService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class PaymentDetailController {

	private final PaymentDetailService service;

	@PreAuthorize("hasRole('STAFF')")
	@GetMapping(path = "/staff/payment-details")
	public ResponseEntity<WebResponse<List<PaymentDetailResponse>>> getAll() {
		return ResponseEntity.ok(
				WebResponse.<List<PaymentDetailResponse>>builder()
						.data(service.getAll())
						.build());
	}

	@PreAuthorize("hasRole('STAFF')")
	@PostMapping(path = "/staff/payment-details")
	public ResponseEntity<WebResponse<PaymentDetailResponse>> add(@RequestBody PaymentDetailRequest request) {
		return ResponseEntity.ok(
				WebResponse.<PaymentDetailResponse>builder()
						.data(service.add(request))
						.message("Pembayaran berhasil ditambah")
						.build());

	}

	@PreAuthorize("hasRole('STAFF')")
	@PatchMapping(path = "/staff/payment-details/{keyword}")
	public ResponseEntity<WebResponse<PaymentDetailResponse>> update(@PathVariable String keyword,
			@RequestBody PaymentDetailUpdate update) {
		return ResponseEntity.ok(
				WebResponse.<PaymentDetailResponse>builder()
						.data(service.update(keyword, update))
						.message("Pembayaran berhasil diedit")
						.build());
	}

	@PreAuthorize("hasRole('STAFF')")
	@DeleteMapping(path = "/staff/payment-details/{keyword}")
	public ResponseEntity<WebResponse<String>> delete(@PathVariable String keyword) {
		service.delete(keyword);
		return ResponseEntity.ok(
				WebResponse.<String>builder()
						.message("Pembayaran berhasil dihapus")
						.build());
	}

	@PreAuthorize("hasRole('STAFF')")
	@GetMapping(path = "/staff/payment-details/{studentId}")
	public ResponseEntity<WebResponse<List<PaymentDetailBillResponse>>> getUnpaidPayments(
			@PathVariable String studentId) {
		return ResponseEntity.ok(
				WebResponse.<List<PaymentDetailBillResponse>>builder()
						.data(service.getUnpaidPayments(studentId))
						.build());
	}

	@PreAuthorize("hasRole('STAFF')")
	@GetMapping(path = "/staff/payment-details/students-bill")
	public ResponseEntity<WebResponse<List<StudentUnpaidResponse>>> getTotalBill() {
		return ResponseEntity.ok(
				WebResponse.<List<StudentUnpaidResponse>>builder()
						.data(service.getStudentsTotalUnpaid())
						.build());
	}

}
