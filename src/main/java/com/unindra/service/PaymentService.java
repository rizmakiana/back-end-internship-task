package com.unindra.service;

import java.util.List;

import com.unindra.model.response.PaymentHistoryResponse;
import com.unindra.model.response.PaymentItemDetail;
import com.unindra.model.response.PaymentRequest;

public interface PaymentService {
    
    String generateReferenceNumber();

    List<PaymentHistoryResponse> getAll();

    PaymentHistoryResponse add(String studentId, PaymentRequest request);

    PaymentItemDetail get(String referenceNumber);

}