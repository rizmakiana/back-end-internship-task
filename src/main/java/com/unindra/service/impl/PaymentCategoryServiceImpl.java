package com.unindra.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.PaymentCategory;
import com.unindra.model.request.PaymentCategoryRequest;
import com.unindra.model.response.PaymentCategoryResponse;
import com.unindra.repository.PaymentCategoryRepository;
import com.unindra.service.PaymentCategoryService;
import com.unindra.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentCategoryServiceImpl implements PaymentCategoryService {

    private final PaymentCategoryRepository repository;

    private final ValidationService validationService;

    @Override
    public PaymentCategoryResponse add(PaymentCategoryRequest request) {
        validationService.validate(request);

        if (repository.existsByName(request.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pembayaran sudah ada");
        }

        PaymentCategory category = new PaymentCategory();
        category.setName(request.getName());

        return getPaymentCategoryResponse(repository.save(category));
    }

    @Override
    public List<PaymentCategoryResponse> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public PaymentCategoryResponse update(String name, PaymentCategoryRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private PaymentCategoryResponse getPaymentCategoryResponse(PaymentCategory category) {
        return PaymentCategoryResponse.builder()
                .name(category.getName())
                .totalPayment(category.getPaymentDetails().size())
                .build();
    }

}
