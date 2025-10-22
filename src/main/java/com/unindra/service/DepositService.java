package com.unindra.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.unindra.entity.Student;
import com.unindra.model.request.DepositRequest;
import com.unindra.model.response.StudentDepositResponse;
import com.unindra.model.util.TransactionType;

public interface DepositService {
    
    void deposit(String studentId, DepositRequest request);

    void withdrawal(String studentId, DepositRequest request);

    String generateWithDrawalReferenceNumber();
    
    String generateDepositReferenceNumber();

    Optional<BigDecimal> sumByStudentAndTransactionType(Student student, TransactionType type);

    List<StudentDepositResponse> getStudentsDeposit();

    
}
