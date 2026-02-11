package com.unindra.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unindra.entity.Deposit;
import com.unindra.entity.Student;
import com.unindra.model.request.DepositRequest;
import com.unindra.model.response.DepositHistoryResponse;
import com.unindra.model.response.DepositReport;
import com.unindra.model.response.DepositTransaction;
import com.unindra.model.response.StudentDepositResponse;
import com.unindra.model.response.StudentDepositsHistory;
import com.unindra.model.util.TransactionType;
import com.unindra.repository.DepositRepository;
import com.unindra.service.DepositService;
import com.unindra.service.StudentService;
import com.unindra.service.ValidationService;
import com.unindra.util.Formatter;
import com.unindra.util.TimeFormat;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository repository;

    private final StudentService studentService;

    private final ValidationService validationService;

    private final ObjectMapper objectMapper;

    @Override
    public StudentDepositResponse deposit(String studentId, DepositRequest request) {
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

        return getStudentDeposit(student);

    }

    @Override
    public StudentDepositResponse withdrawal(String studentId, DepositRequest request) {
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

        BigDecimal currentSavings = repository.sumByStudentAndTransactionType(student, TransactionType.DEPOSIT)
                .orElse(BigDecimal.ZERO);

        if (amount.compareTo(currentSavings) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo tabungan tidak mencukupi");
        }

        Deposit deposit = new Deposit();
        deposit.setStudent(student);
        deposit.setDate(depositDate);
        deposit.setTransactionType(TransactionType.WITHDRAW);
        deposit.setAmount(amount);
        deposit.setReferenceNumber(request.getReferenceNo());

        repository.save(deposit);

        return getStudentDeposit(student);
    }

    @Override
    public String generateReferenceNumber(String type) {
        TransactionType transactionType;
        if ("deposit".equalsIgnoreCase(type)) {
            transactionType = TransactionType.DEPOSIT;
        } else if ("withdraw".equalsIgnoreCase(type)) {
            transactionType = TransactionType.WITHDRAW;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipe transaksi tidak valid");
        }

        String keyword = (transactionType == TransactionType.DEPOSIT) ? "DEPOSIT" : "WITHDRAW";
        long count = repository.countByTransactionType(transactionType);
        long next = count + 1;

        // Contoh hasil: S/WITHDRAWAL-0001
        return String.format("S/%s-%04d", keyword, next);
    }

    @Override
    public Optional<BigDecimal> sumByStudentAndTransactionType(Student student, TransactionType type) {
        return repository.sumByStudentAndTransactionType(student, type);
    }

    @Override
    public List<StudentDepositResponse> getStudentsDeposit() {
        return studentService.findAll().stream()
                .map(student -> getStudentDeposit(student))
                .toList();
    }

    @Override
    public StudentDepositResponse getStudentDeposit(Student student) {
        BigDecimal totalDeposit = repository.sumByStudentAndTransactionType(student, TransactionType.DEPOSIT)
                .orElse(BigDecimal.ZERO);

        BigDecimal totalWithDraw = repository
                .sumByStudentAndTransactionType(student, TransactionType.WITHDRAW)
                .orElse(BigDecimal.ZERO);

        BigDecimal balance = totalDeposit.subtract(totalWithDraw);

        return StudentDepositResponse.builder()
                .studentId(student.getStudentId())
                .studentName(student.getName())
                .totalDeposit(balance)
                .build();

    }

    @Override
    public List<StudentDepositsHistory> getStudentDepositHistory(String studentId) {

        Student student = studentService.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Data siswa tidak ditemukan"));

        return student.getDeposits().stream()
                .map(deposit -> {
                    BigDecimal dept = BigDecimal.ZERO;
                    BigDecimal wd = BigDecimal.ZERO;

                    if (deposit.getTransactionType() == TransactionType.DEPOSIT) {
                        dept = deposit.getAmount();
                    } else {
                        wd = deposit.getAmount();

                    }

                    return StudentDepositsHistory.builder()
                            .referenceNo(deposit.getReferenceNumber())
                            .date(deposit.getDate().format(TimeFormat.formatter2))
                            .depositAmount(dept)
                            .withdrawAmount(wd)
                            .build();
                }).toList();
    }

    @Override
    public List<DepositHistoryResponse> getAllHistory() {
        return repository.findAll().stream()
                .map(deposit -> DepositHistoryResponse.builder()
                        .referenceNumber(deposit.getReferenceNumber())
                        .studentName(deposit.getStudent().getName())
                        .date(deposit.getDate().format(TimeFormat.formatter2))
                        .depositAmount(formatAmount(deposit, TransactionType.DEPOSIT))
                        .withdrawalAmount(formatAmount(deposit, TransactionType.WITHDRAW))
                        .build())
                .toList();
    }

    private String formatAmount(Deposit deposit, TransactionType type) {
        return deposit.getTransactionType() == type
                ? deposit.getAmount().toPlainString()
                : "0";
    }

    @Override
    public byte[] generateStudentPdf(String studentId) {

        Student student = studentService.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Data siswa tidak ditemukan"));

        BigDecimal totalDeposit = repository.sumByStudentAndTransactionType(student, TransactionType.DEPOSIT)
                .orElse(BigDecimal.ZERO);

        BigDecimal totalWithDraw = repository
                .sumByStudentAndTransactionType(student, TransactionType.WITHDRAW)
                .orElse(BigDecimal.ZERO);

        BigDecimal balance = totalDeposit.subtract(totalWithDraw);

        List<StudentDepositsHistory> studentDepositHistory = getStudentDepositHistory(studentId);

        // Gunakan NumberFormat kalau mau format ribuan (110.171.101) di JSON
        // Kalau cuma toPlainString() hasilnya cuma angka polos (110171101)

        DepositReport report = DepositReport.builder()
                .name(student.getName())
                .id(studentId)
                .totalDeposit(Formatter.formatToIndonesian(totalDeposit.toPlainString()))
                .totalWithdrawal(Formatter.formatToIndonesian(totalWithDraw.toPlainString()))
                .currentDeposit(Formatter.formatToIndonesian(balance.toPlainString())) // Set saldo di sini
                .transactions(
                        studentDepositHistory.stream().map(transaction ->
                        // Langsung kembalikan builder tanpa kurung kurawal agar implisit return
                        DepositTransaction.builder()
                                .date(transaction.getDate().toString())
                                .reference(transaction.getReferenceNo())
                                .deposit(transaction.getDepositAmount().compareTo(BigDecimal.ZERO) > 0
                                        ? Formatter.formatToIndonesian(transaction.getDepositAmount().toPlainString())
                                        : "-")
                                .withdrawal(transaction.getWithdrawAmount().compareTo(BigDecimal.ZERO) > 0
                                        ? Formatter.formatToIndonesian(transaction.getWithdrawAmount().toPlainString())
                                        : "-")
                                .build()).toList() // Jangan lupa diubah ke List (Java 16+)
                )
                .build();

        // 3. COMPILATION LOGIC: Typst CLI
        String uniqueId = UUID.randomUUID().toString();
        String projectRoot = System.getProperty("user.dir");
        Path typstRoot = Paths.get(projectRoot, "assets/typst"); // Folder sejajar src

        Path jsonFile = typstRoot.resolve("data/dep-" + uniqueId + ".json");
        Path pdfFile = typstRoot.resolve("output/dep-" + uniqueId + ".pdf");

        try {
            // Pastikan folder ada
            Files.createDirectories(jsonFile.getParent());
            Files.createDirectories(pdfFile.getParent());

            // Tulis data ke JSON
            objectMapper.writeValue(jsonFile.toFile(), report);

            // Jalankan Typst
            ProcessBuilder pb = new ProcessBuilder(
                    "typst", "compile",
                    typstRoot.resolve("report/depositReport.typ").toString(),
                    pdfFile.toString(),
                    "--root", typstRoot.toString(),
                    "--input", "dataFile=data/" + jsonFile.getFileName().toString());

            pb.redirectErrorStream(true);
            Process process = pb.start();

            if (!process.waitFor(30, TimeUnit.SECONDS)) {
                process.destroy();
                throw new RuntimeException("Typst timeout");
            }

            if (process.exitValue() != 0) {
                String error = new String(process.getInputStream().readAllBytes());
                throw new RuntimeException("Typst Error: " + error);
            }

            // 4. BACA HASIL & RETURN
            return Files.readAllBytes(pdfFile);

        } catch (Exception e) {
            throw new RuntimeException("Gagal generate PDF Deposit", e);
        } finally {
            // 5. CLEANUP
            try {
                Files.deleteIfExists(jsonFile);
                Files.deleteIfExists(pdfFile);
            } catch (IOException ignored) {
            }
        }
    }

}
