package com.unindra.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.Classroom;
import com.unindra.entity.Payment;
import com.unindra.entity.PaymentDetail;
import com.unindra.entity.PaymentItem;
import com.unindra.entity.Student;
import com.unindra.model.response.PaymentDetailBillResponse;
import com.unindra.model.response.PaymentHistoryResponse;
import com.unindra.model.response.PaymentItemDetail;
import com.unindra.model.response.PaymentRequest;
import com.unindra.repository.PaymentRepository;
import com.unindra.service.PaymentDetailService;
import com.unindra.service.PaymentService;
import com.unindra.service.StudentService;
import com.unindra.service.ValidationService;
import com.unindra.util.TimeFormat;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    private final ValidationService validationService;

    private final StudentService studentService;

    private final PaymentDetailService paymentDetailService;

    @Override
    public String generateReferenceNumber() {

        return String.format("S/TRX/%04d", repository.count() + 1);

    }

    @Override
    public List<PaymentHistoryResponse> getAll() {
        return repository.findAll().stream()
                .map(payment -> PaymentHistoryResponse.builder()
                        .referenceNumber(payment.getReferenceNumber())
                        .studentName(payment.getStudent().getName())
                        .date(payment.getDate().format(TimeFormat.formatter2))
                        .totalAmount(payment.getTotal())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public PaymentHistoryResponse add(String studentId, PaymentRequest request) {
        validationService.validate(request);

        if (repository.existsByReferenceNumber(request.getReferenceNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reference number sudah digunakan");
        }

        Student student = studentService.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Siswa tidak ditemukan"));

        Classroom classroom = student.getSection().getClassroom();

        List<PaymentItem> items = new ArrayList<>();

        for (PaymentDetailBillResponse bill : request.getPayments()) {
            // Cari PaymentDetail (prioritaskan yang sesuai classroom, kalau gak ada
            // fallback ke null classroom)
            PaymentDetail paymentDetail = paymentDetailService
                    .getByCategoryNameAndPaymentNameAndClassroom(bill.getPaymentCategory(), bill.getName(), classroom)
                    .orElseGet(() -> paymentDetailService
                            .getByCategoryNameAndPaymentNameAndClassroomIsNull(bill.getPaymentCategory(),
                                    bill.getName())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "Payment detail tidak ditemukan: " + bill.getName())));

            PaymentItem item = new PaymentItem();
            item.setPaymentDetail(paymentDetail);
            items.add(item);
        }

        BigDecimal totalPayment = BigDecimal.ZERO;
        for (PaymentItem item : items) {
            totalPayment = totalPayment.add(item.getPaymentDetail().getUnitPrice());
        }

        if (totalPayment.compareTo(request.getAmount()) != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nominal pembayaran tidak sesuai dengan total tagihan");
        }

        Payment payment = new Payment();
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setStudent(student);
        payment.setDate(LocalDateTime.now());
        payment.setTotal(totalPayment);

        for (PaymentItem item : items) {
            item.setPayment(payment);
        }
        payment.setPaymentItems(items);

        repository.save(payment);

        return PaymentHistoryResponse.builder()
                .referenceNumber(payment.getReferenceNumber())
                .studentName(student.getName())
                .date(payment.getDate().format(TimeFormat.formatter2))
                .totalAmount(totalPayment)
                .build();
    }

    @Override
    public PaymentItemDetail get(String referenceNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}
