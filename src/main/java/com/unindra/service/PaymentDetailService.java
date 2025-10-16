package com.unindra.service;

import java.util.List;

import com.unindra.model.request.PaymentDetailRequest;
import com.unindra.model.request.PaymentDetailUpdate;
import com.unindra.model.response.PaymentDetailResponse;

public interface PaymentDetailService {

    List<PaymentDetailResponse> getAll();

    PaymentDetailResponse add(PaymentDetailRequest request);

    PaymentDetailResponse update(String categoryAndClassroomAndPaymentName, PaymentDetailUpdate request);

    void delete(String categoryAndClassroomAndPaymentName);
    
}