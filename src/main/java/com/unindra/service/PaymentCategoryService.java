package com.unindra.service;

import java.util.List;

import com.unindra.model.request.PaymentCategoryRequest;
import com.unindra.model.response.PaymentCategoryResponse;

public interface PaymentCategoryService {

    PaymentCategoryResponse add(PaymentCategoryRequest request);

    List<PaymentCategoryResponse> getAll();

    PaymentCategoryResponse update(String name, PaymentCategoryRequest request);

    void delete(String name);
    
}