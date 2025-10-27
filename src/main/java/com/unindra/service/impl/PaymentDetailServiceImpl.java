package com.unindra.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.Classroom;
import com.unindra.entity.PaymentCategory;
import com.unindra.entity.PaymentDetail;
import com.unindra.entity.Student;
import com.unindra.model.request.PaymentDetailRequest;
import com.unindra.model.request.PaymentDetailUpdate;
import com.unindra.model.response.PaymentDetailBillResponse;
import com.unindra.model.response.PaymentDetailResponse;
import com.unindra.model.response.StudentUnpaidResponse;
import com.unindra.repository.ClassroomRepository;
import com.unindra.repository.PaymentDetailRepository;
import com.unindra.service.PaymentCategoryService;
import com.unindra.service.PaymentDetailService;
import com.unindra.service.StudentService;
import com.unindra.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentDetailServiceImpl implements PaymentDetailService {

	private final PaymentDetailRepository repository;

	private final ValidationService validationService;

	private final PaymentCategoryService categoryService;

	private final ClassroomRepository classroomRepository;

	private final StudentService studentService;

	@Override
	public List<PaymentDetailResponse> getAll() {
		return repository.findAll().stream()
				.map(detail -> getPaymentDetailResponse(detail))
				.toList();
	}

	@Override
	public PaymentDetailResponse add(PaymentDetailRequest request) {
		validationService.validate(request);

		PaymentCategory category = categoryService.findByName(request.getCategoryName())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Kategori pembayaran tidak ditemukan"));

		Classroom classroom = classroomRepository.findByCode(request.getClassroomCode()).orElse(null);

		if (repository.existsByPaymentCategoryAndClassroomAndName(category, classroom, request.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Pembayaran sudah ada");
		}

		BigDecimal amount = Optional.ofNullable(request.getUnitPrice())
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.map(a -> {
					try {
						return new BigDecimal(a);
					} catch (NumberFormatException e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
								"Nominal pembayaran tidak valid");
					}
				})
				.filter(a -> a.compareTo(BigDecimal.ZERO) > 0)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Nominal pembayaran harus lebih dari Rp. 0"));

		PaymentDetail paymentDetail = new PaymentDetail();
		paymentDetail.setPaymentCategory(category);
		paymentDetail.setClassroom(classroom);
		paymentDetail.setName(request.getName());
		paymentDetail.setUnitPrice(amount);

		return getPaymentDetailResponse(repository.save(paymentDetail));

	}

	@Override
	public PaymentDetailResponse update(String categoryAndClassroomAndPaymentName, PaymentDetailUpdate request) {
		validationService.validate(request);

		String[] keyword = categoryAndClassroomAndPaymentName.split("-");

		PaymentCategory category = categoryService.findByName(keyword[0])
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Kategori pembayaran tidak ditemukan"));

		Classroom classroom = classroomRepository.findByCode(keyword[1]).orElse(null);

		PaymentDetail detail = repository
				.findByPaymentCategoryAndClassroomAndName(category, classroom, keyword[2])
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Pembayaran tidak ditemukan"));

		boolean paymentNameChanged = !detail.getName().equals(request.getName());
		boolean isPaymentAlreadyExists = repository.existsByPaymentCategoryAndClassroomAndName(category,
				classroom,
				request.getName());
		if (paymentNameChanged && isPaymentAlreadyExists) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pembayaran sudah ada");
		}

		BigDecimal amount = Optional.ofNullable(request.getUnitPrice())
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.map(a -> {
					try {
						return new BigDecimal(a);
					} catch (NumberFormatException e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
								"Nominal pembayaran tidak valid");
					}
				})
				.filter(a -> a.compareTo(BigDecimal.ZERO) > 0)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Nominal pembayaran harus lebih dari Rp. 0"));

		// check if unit price changed and has a payments
		if (!detail.getUnitPrice().equals(amount) && !detail.getPaymentItems().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Tidak dapat mengubah harga pembayaran karena sudah ada pembayaran masuk");
		}

		detail.setName(request.getName());
		detail.setUnitPrice(amount);

		return getPaymentDetailResponse(repository.save(detail));
	}

	@Override
	public void delete(String categoryAndClassroomAndPaymentName) {
		String[] keyword = categoryAndClassroomAndPaymentName.split("-");

		PaymentCategory category = categoryService.findByName(keyword[0])
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Kategori pembayaran tidak ditemukan"));

		Classroom classroom = classroomRepository.findByCode(keyword[1]).orElse(null);

		PaymentDetail detail = repository
				.findByPaymentCategoryAndClassroomAndName(category, classroom, keyword[2])
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Pembayaran tidak ditemukan"));

		if (!detail.getPaymentItems().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Tidak dapat menghapus pembayaran karena sudah ada pembayaran masuk");
		}

		repository.delete(detail);
	}

	private PaymentDetailResponse getPaymentDetailResponse(PaymentDetail paymentDetail) {

		String classroomCode = Optional.ofNullable(paymentDetail.getClassroom())
				.map(Classroom::getCode)
				.orElse("Regular");

		return PaymentDetailResponse.builder()
				.categoryName(paymentDetail.getPaymentCategory().getName())
				.paymentName(paymentDetail.getName())
				.classroomCode(classroomCode)
				.unitPrice(paymentDetail.getUnitPrice()
						.stripTrailingZeros()
						.toPlainString())
				.build();
	}

	@Override
	public List<PaymentDetailBillResponse> getUnpaidPayments(String studentId) {
		Student student = studentService.findByStudentId(studentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Siswa tidak ditemukan"));

		Classroom classroom = student.getSection().getClassroom();
		return repository.findUnpaidBillByStudent(student, classroom);
	}

	@Override
	public List<StudentUnpaidResponse> getStudentsTotalUnpaid() {
		return repository.findStudentsTotalUnpaid();
	}

	@Override
	public Optional<PaymentDetail> getByCategoryNameAndPaymentNameAndClassroom(String categoryName, String name,
			Classroom classroom) {
		return repository.findByCategoryNameAndPaymentNameAndClassroom(categoryName, name, classroom);
	}

	@Override
	public Optional<PaymentDetail> getByCategoryNameAndPaymentNameAndClassroomIsNull(String categoryName, String name) {
		return repository.findByCategoryNameAndPaymentNameAndClassroomIsNull(categoryName, name);
	}

}