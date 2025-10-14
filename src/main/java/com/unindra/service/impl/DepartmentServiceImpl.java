package com.unindra.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.Department;
import com.unindra.model.request.DepartmentRequest;
import com.unindra.model.response.DepartmentResponse;
import com.unindra.repository.DepartmentRepository;
import com.unindra.service.DepartmentService;
import com.unindra.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    private final ValidationService validationService;

    @Override
    public DepartmentResponse add(DepartmentRequest request) {
        validationService.validate(request);

        if (repository.existsByName(request.getDepartmentName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Jurusan %s sudah ada", request.getDepartmentName()));
        }

        if (repository.existsByCode(request.getCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Kode Jurusan %s sudah digunakan", request.getCode()));
        }

        Department department = new Department();

        department.setName(request.getDepartmentName());
        department.setCode(request.getCode());

        return getDepartmentResponse(repository.save(department));
    }

    @Override
    public List<DepartmentResponse> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public DepartmentResponse update(String code, DepartmentRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public DepartmentResponse getDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .departmentName(department.getName())
                .code(department.getCode())
                .totalClassroom(department.getClassrooms().size())
                .build();
    }
}
