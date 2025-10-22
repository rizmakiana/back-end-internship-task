package com.unindra.service;

import java.util.List;
import java.util.Optional;

import com.unindra.entity.Student;
import com.unindra.model.request.StudentRequest;
import com.unindra.model.request.StudentUpdate;
import com.unindra.model.response.StudentDepositResponse;
import com.unindra.model.response.StudentResponse;
import com.unindra.model.response.StudentTable;

public interface StudentService {
    
    StudentResponse add(StudentRequest request);

    StudentResponse getByStudentId(String studentId);

    List<StudentTable> getAll();

    StudentResponse update(String studentId, StudentUpdate request);

    void delete(String studentId);

    Optional<Student> findByStudentId(String studentId);

    List<StudentDepositResponse> getStudentsDeposit();

}