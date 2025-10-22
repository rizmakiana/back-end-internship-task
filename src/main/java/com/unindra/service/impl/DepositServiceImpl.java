package com.unindra.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unindra.entity.Student;
import com.unindra.model.request.DepositRequest;
import com.unindra.model.util.TransactionType;
import com.unindra.repository.DepositRepository;
import com.unindra.service.DepositService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository repository;

    @Override
    public void deposit(Student student, DepositRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdrawal'");        
    }

    @Override
    public void withdrawal(Student student, DepositRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdrawal'");
    }

    @Override
    public String generateWithDrawalReferenceNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateWithDrawalReferenceNumber'");
    }

    @Override
    public String generateDepositReferenceNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateDepositReferenceNumber'");
    }

    @Override
    public Optional<BigDecimal> sumByStudentAndTransactionType(Student student, TransactionType type) {
        return repository.sumByStudentAndTransactionType(student, type);
    }

    

}
