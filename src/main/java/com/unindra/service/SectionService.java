package com.unindra.service;

import java.util.List;

import com.unindra.model.request.SectionRequest;
import com.unindra.model.request.SectionUpdateRequest;
import com.unindra.model.response.SectionResponse;

public interface SectionService {
    
    SectionResponse add(SectionRequest request);

    List<SectionResponse> getAll();

    SectionResponse update(String code, SectionUpdateRequest request);

    void delete(String code);

}