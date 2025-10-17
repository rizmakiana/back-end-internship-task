package com.unindra.service.impl;

import java.util.List;
import java.util.Optional;

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
        return repository.findAll().stream()
                .map(category -> getPaymentCategoryResponse(category))
                .toList();
    }

    @Override
    public PaymentCategoryResponse update(String name, PaymentCategoryRequest request) {
        validationService.validate(request);
        
        PaymentCategory category = repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Kategori pembayaran tidak ditemukan"));

        if (repository.existsByNameAndIdNot(name, category.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Kategori pembayaran sudah ada");
        }

        category.setName(request.getName());

        return getPaymentCategoryResponse(repository.save(category));
    }

    @Override
    public void delete(String name) {
        
        PaymentCategory category = repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Kategori pembayaran tidak ditemukan"));
        
        if (!category.getPaymentDetails().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Tidak dapat menghapus karena masih memiliki beberapa pembayaran");
        }

        repository.delete(category);
    }

    private PaymentCategoryResponse getPaymentCategoryResponse(PaymentCategory category) {

        int totalPayment = Optional.ofNullable(category.getPaymentDetails())
            .map(List::size)
            .orElse(0);

        return PaymentCategoryResponse.builder()
                .name(category.getName())
                .totalPayment(totalPayment)
                .build();
    }

    @Override
    public Optional<PaymentCategory> findByName(String name) {
        return repository.findByName(name);
    }

}
