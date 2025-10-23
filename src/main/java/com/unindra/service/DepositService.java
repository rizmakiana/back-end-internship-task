package com.unindra.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.unindra.entity.Student;
import com.unindra.model.request.DepositRequest;
import com.unindra.model.response.StudentDepositResponse;
import com.unindra.model.response.StudentDepositsHistory;
import com.unindra.model.util.TransactionType;

public interface DepositService {
    
    StudentDepositResponse deposit(String studentId, DepositRequest request);

    StudentDepositResponse withdrawal(String studentId, DepositRequest request);

    String generateReferenceNumber(String type);

    Optional<BigDecimal> sumByStudentAndTransactionType(Student student, TransactionType type);

    List<StudentDepositResponse> getStudentsDeposit();

    StudentDepositResponse getStudentDeposit(Student student);

    List<StudentDepositsHistory> getStudentDepositHistory(String studentId);

    
}
