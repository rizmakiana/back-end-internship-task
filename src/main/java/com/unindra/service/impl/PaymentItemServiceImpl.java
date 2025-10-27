package com.unindra.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unindra.model.response.PaymentHistoryResponse;
import com.unindra.model.response.PaymentItemDetail;
import com.unindra.model.response.PaymentRequest;
import com.unindra.repository.PaymentItemRepository;
import com.unindra.service.PaymentItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentItemServiceImpl implements PaymentItemService{

    private final PaymentItemRepository repository;

    @Override
    public String generateReferenceNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateReferenceNumber'");
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
