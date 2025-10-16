package com.unindra.service;

import java.util.List;

import com.unindra.model.request.PaymentDetailRequest;
import com.unindra.model.response.PaymentDetailResponse;

public interface PaymentDetailService {

    List<PaymentDetailResponse> getAll();

    PaymentDetailResponse add(PaymentDetailRequest request);

    PaymentDetailResponse update(String categoryAndPaymentName, PaymentDetailRequest request);

    void delete(String categoryAndPaymentName);
    
}