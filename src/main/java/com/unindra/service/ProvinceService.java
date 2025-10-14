package com.unindra.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.model.response.ProvinceResponse;
import com.unindra.repository.ProvinceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProvinceService {

    private final ProvinceRepository repository;

    public List<ProvinceResponse> getProvinceList() {
        return repository.findAll().stream()
                .map(province -> ProvinceResponse.builder()
                        .id(province.getId())
                        .name(province.getName())
                        .build())
                .toList();
    }

    public ProvinceResponse getProvinceById(String id) {
        return repository.findById(id)
                .map(result -> ProvinceResponse.builder()
                        .id(result.getId())
                        .name(result.getName())
                        .build())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

}