package com.unindra.service.impl;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.Deposit;
import com.unindra.entity.Student;
import com.unindra.model.request.DepositRequest;
import com.unindra.model.response.StudentDepositResponse;
import com.unindra.model.util.TransactionType;
import com.unindra.repository.DepositRepository;
import com.unindra.service.DepositService;
import com.unindra.service.StudentService;
import com.unindra.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository repository;

    private final StudentService studentService;

    private final ValidationService validationService;

    @Override
    public void deposit(String studentId, DepositRequest request) {
        validationService.validate(request);

        Student student = studentService.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Data siswa tidak ditemukan"));

        LocalDateTime depositDate;
        try {
            depositDate = LocalDateTime.of(
                    Integer.parseInt(request.getYear()),
                    request.getMonth(),
                    Integer.parseInt(request.getDate()),
                    Integer.parseInt(request.getClock()),
                    Integer.parseInt(request.getMinute()));
        } catch (DateTimeException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tanggal tidak valid");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(request.getAmount().trim());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nominal Tabungan tidak valid");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nominal Tabungan harus lebih dari Rp. 0");
        }

        Deposit deposit = new Deposit();
        deposit.setStudent(student);
        deposit.setDate(depositDate);
        deposit.setTransactionType(TransactionType.DEPOSIT);
        deposit.setAmount(amount);
        deposit.setReferenceNumber(request.getReferenceNo());

        repository.save(deposit);

    }

    @Override
    public void withdrawal(String studentId, DepositRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdrawal'");
    }

    @Override
    public String generateWithDrawalReferenceNumber() {
            long count = repository.countByTransactionType(TransactionType.WITHDRAW);
        long next = count + 1;

        // Contoh hasil: S/DEPOSIT-0001
        return String.format("S/DEPOSIT-%04d", next);
    }

    @Override
    public String generateDepositReferenceNumber() {
        long count = repository.countByTransactionType(TransactionType.DEPOSIT);
        long next = count + 1;

        // Contoh hasil: S/DEPOSIT-0001
        return String.format("S/DEPOSIT-%04d", next);
    }

    @Override
    public Optional<BigDecimal> sumByStudentAndTransactionType(Student student, TransactionType type) {
        return repository.sumByStudentAndTransactionType(student, type);
    }

    @Override
    public List<StudentDepositResponse> getStudentsDeposit() {
        return studentService.findAll().stream()
                .map(student -> {
                    BigDecimal totalDeposit = repository
                            .sumByStudentAndTransactionType(student, TransactionType.DEPOSIT)
                            .orElse(BigDecimal.ZERO);

                    BigDecimal totalWithDraw = repository
                            .sumByStudentAndTransactionType(student, TransactionType.WITHDRAW)
                            .orElse(BigDecimal.ZERO);

                    BigDecimal balance = totalDeposit.subtract(totalWithDraw);

                    return StudentDepositResponse.builder()
                            .totalDeposit(balance)
                            .build();
                })
                .toList();
    }

}
