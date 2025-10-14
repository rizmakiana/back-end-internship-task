package com.unindra.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.Classroom;
import com.unindra.entity.Department;
import com.unindra.model.request.ClassroomRequest;
import com.unindra.model.response.ClassroomResponse;
import com.unindra.repository.ClassroomRepository;
import com.unindra.service.ClassroomService;
import com.unindra.service.DepartmentService;
import com.unindra.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository repository;

    private final DepartmentService departmentService;

    private final ValidationService validationService;

    @Override
    public ClassroomResponse add(ClassroomRequest request) {
        validationService.validate(request);

        Department department = departmentService.findByDepartmentName(request.getDepartmentName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jurusan tidak ditemukan"));

        // check if deparment has a classroom grade level
        if (repository.existsByDepartmentAndGradeLevel(department, request.getGradeLevel())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Tingkat kelas %s sudah ada", request.getGradeLevel()));
        }

        Classroom classroom = new Classroom();

        classroom.setDepartment(department);
        classroom.setGradeLevel(request.getGradeLevel());
        classroom.setCode(department.getCode() + request.getGradeLevel());
        
        return getClassroomResponse(repository.save(classroom));

    }

    @Override
    public List<ClassroomResponse> getAll() {
        return repository.findAll().stream()
                .map(classroom -> getClassroomResponse(classroom))
                .toList();
    }

    @Override
    public void delete(String code) {
        Classroom classroom = repository.findByCode(code)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tingkat kelas tidak ditemukan"));

        if (!classroom.getSections().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tidak dapat dihapus karena tingkat kelas memiliki kelas aktif");
        }

        repository.delete(classroom);
    }

    public ClassroomResponse getClassroomResponse(Classroom classroom) {
        return ClassroomResponse.builder()
                .code(classroom.getCode())
                .departmentName(classroom.getDepartment().getName())
                .gradeLevel(classroom.getGradeLevel())
                .totalSection(classroom.getSections().size())
                .build();
    }
}
