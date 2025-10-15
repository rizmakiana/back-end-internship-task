package com.unindra.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public StudentResponse getStudentDetail(Student student) {
        return StudentResponse.builder()
                .studentId(student.getStudentId())
                .name(student.getName())
                .gender(student.getGender())
                .regencyId(student.getBirthplace().getId())
                .birthDate(student.getBirthDate())
                .districtId(student.getDistrictAddress().getId())
                .address(student.getAddress())
                .username(student.getUsername())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .department(student.getSection().getClassroom().getDepartment().getName())
                .classroom(student.getSection().getClassroom().getGradeLevel())
                .section(String.valueOf(student.getSection().getName()))
                .build();
    }

    @Override
    public StudentResponse getByStudentId(String studentId) {
        return repository.findByStudentId(studentId)
                .map(student -> getStudentDetail(student))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Siswa tidak ditemukan"));
    }

}
