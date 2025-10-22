package com.unindra.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.unindra.entity.Student;
import com.unindra.model.request.DepositRequest;
import com.unindra.model.util.TransactionType;

public interface DepositService {
    
    void deposit(Student student, DepositRequest request);

    void withdrawal(Student student, DepositRequest request);

    String generateWithDrawalReferenceNumber();
    
    String generateDepositReferenceNumber();

    Optional<BigDecimal> sumByStudentAndTransactionType(Student student, TransactionType type);


    
}
