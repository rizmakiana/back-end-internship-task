package com.unindra.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.Classroom;
import com.unindra.entity.Department;
import com.unindra.entity.Section;
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
        return repository.findAll().stream()
                .map(department -> getDepartmentResponse(department))
                .toList();
    }

    @Override
    public DepartmentResponse update(String code, DepartmentRequest request) {
        validationService.validate(request);

        Department department = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurusan tidak ditemukan"));

        // check if new department name already used
        if (repository.existsByNameAndIdNot(request.getDepartmentName(), department.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Jurusan '%s' sudah ada", request.getDepartmentName()));
        }

        // check if new department code already used
        if (repository.existsByCodeAndIdNot(request.getCode(), department.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Kode jurusan '%s' sudah digunakan", request.getCode()));
        }

        department.setCode(request.getCode());
        department.setName(request.getDepartmentName());

        if (!department.getClassrooms().isEmpty()) {
            for (Classroom classroom : department.getClassrooms()) {
                String newClassroomCode = request.getCode() + classroom.getGradeLevel();
                classroom.setCode(newClassroomCode);

                if (!classroom.getSections().isEmpty()) {
                    for (Section section : classroom.getSections()) {
                        String newSectionCode = newClassroomCode + " " + section.getName();
                        section.setCode(newSectionCode);
                    }
                }
            }

        }

        Department updated = repository.save(department);
        return getDepartmentResponse(updated);
    }

    @Override
    public void delete(String code) {

        Department department = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jurusan tidak ditemukan"));

        // check if department has classroom
        if (!department.getClassrooms().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Tidak dapat dihapus karena jurusan memiliki kelas aktif");
        }

        repository.delete(department);
    }

    public DepartmentResponse getDepartmentResponse(Department department) {

        int totalClassroom = Optional.ofNullable(department.getClassrooms())
            .map(List::size)
            .orElse(0);

        return DepartmentResponse.builder()
                .departmentName(department.getName())
                .code(department.getCode())
                .totalClassroom(totalClassroom)
                .build();
    }

    @Override
    public Optional<Department> findByDepartmentName(String name) {
        return repository.findByName(name);
    }
}
