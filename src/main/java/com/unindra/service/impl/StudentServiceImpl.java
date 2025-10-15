package com.unindra.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unindra.entity.Student;
import com.unindra.model.request.StudentRequest;
import com.unindra.model.response.StudentResponse;
import com.unindra.repository.StudentRepository;
import com.unindra.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public StudentResponse add(StudentRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public List<StudentResponse> getAll() {
        return repository.findAll().stream()
            .map(student -> getStudentTableData(student))
            .toList();
    }

    @Override
    public StudentResponse update(String studentId, StudentRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String studentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public StudentResponse getStudentTableData(Student student) {
        return StudentResponse.builder()
                .studentId(student.getStudentId())
                .name(student.getName())
                .gender(student.getGender())
                .regencyId(student.getBirthplace().getName())
                .birthDate(student.getBirthDate())
                .department(student.getSection().getClassroom().getDepartment().getName())
                .classroom(student.getSection().getClassroom().getGradeLevel())
                .section(String.valueOf(student.getSection().getName()))
                .build();
    }

}
