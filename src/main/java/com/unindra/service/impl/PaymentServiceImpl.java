package com.unindra.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unindra.model.response.PaymentHistoryResponse;
import com.unindra.model.response.PaymentItemDetail;
import com.unindra.model.response.PaymentRequest;
import com.unindra.repository.PaymentRepository;
import com.unindra.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository repository;

    @Override
    public String generateReferenceNumber() {
        
        return String.format("S/TRX/%04d", String.valueOf(repository.count() + 1));

    }

    @Override
    public List<PaymentHistoryResponse> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public PaymentHistoryResponse add(PaymentRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public PaymentItemDetail get(String referenceNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
    
}
