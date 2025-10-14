package com.unindra.service;

import java.util.List;
import java.util.Optional;

import com.unindra.entity.Department;
import com.unindra.model.request.DepartmentRequest;
import com.unindra.model.response.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse add(DepartmentRequest request);

    List<DepartmentResponse> getAll();

    DepartmentResponse update(String code, DepartmentRequest request);

    void delete(String code);

    Optional<Department> findByDepartmentName(String name);
    
}