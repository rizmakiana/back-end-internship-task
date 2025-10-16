package com.unindra.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unindra.entity.PaymentDetail;
import com.unindra.model.request.PaymentDetailRequest;
import com.unindra.model.response.PaymentDetailResponse;
import com.unindra.repository.PaymentDetailRepository;
import com.unindra.service.PaymentDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentDetailServiceImpl implements PaymentDetailService {

    private final PaymentDetailRepository repository;
    
    @Override
    public List<PaymentDetailResponse> getAll() {
        return repository.findAll().stream()
            .map(detail -> getPaymentDetailResponse(detail))
            .toList();
    }

    @Override
    public PaymentDetailResponse add(PaymentDetailRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public PaymentDetailResponse update(String categoryAndPaymentName, PaymentDetailRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String categoryAndPaymentName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private PaymentDetailResponse getPaymentDetailResponse(PaymentDetail paymentDetail) {
        return PaymentDetailResponse.builder()
            .categoryName(paymentDetail.getPaymentCategory().getName())
            .paymentName(paymentDetail.getName())
            .classroomCode(paymentDetail.getClassroom().getCode())
            .unitPrice(paymentDetail.getUnitPrice().toPlainString())
            .build();
    }
    
}