package com.unindra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.District;
import com.unindra.model.response.DistrictResponse;
import com.unindra.repository.DistrictRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository repository;

    public List<DistrictResponse> getDistrictListByRegencyId(String regencyId) {
        return repository.findByRegencyId(regencyId).stream()
                .map(district -> DistrictResponse.builder()
                        .id(district.getId())
                        .name(district.getName())
                        .build())
                .toList();
    }

    public DistrictResponse getDistrictById(String id) {
        return repository.findById(id)
                .map(result -> DistrictResponse.builder()
                        .id(result.getId())
                        .name(result.getName()).build())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    public Optional<District> findById(String id) {
        return repository.findById(id);
    }
}