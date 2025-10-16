package com.unindra.service;

import java.util.List;
import java.util.Optional;

import com.unindra.entity.PaymentCategory;
import com.unindra.model.request.PaymentCategoryRequest;
import com.unindra.model.response.PaymentCategoryResponse;

public interface PaymentCategoryService {

    PaymentCategoryResponse add(PaymentCategoryRequest request);

    List<PaymentCategoryResponse> getAll();

    PaymentCategoryResponse update(String name, PaymentCategoryRequest request);

    void delete(String name);

    Optional<PaymentCategory> findByName(String name);
    
}