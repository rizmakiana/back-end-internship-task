package com.unindra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.Regency;
import com.unindra.model.response.RegencyResponse;
import com.unindra.repository.RegencyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegencyService {

    private final RegencyRepository repository;

    public List<RegencyResponse> getRegencyListByProvinceId(String provinceId) {
        return repository.findByProvinceId(provinceId).stream()
                .map(regency -> RegencyResponse.builder()
                        .id(regency.getId())
                        .name(regency.getName())
                        .build())
                .toList();
    }

    public RegencyResponse getById(String id) {
        return repository.findById(id)
                .map(result -> RegencyResponse.builder()
                        .id(result.getId())
                        .name(result.getName())
                        .build())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    public Optional<Regency> findById(String id) {
        return repository.findById(id);
    }
}