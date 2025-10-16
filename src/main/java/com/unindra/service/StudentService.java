package com.unindra.service;

import java.util.List;

import com.unindra.model.request.StudentRequest;
import com.unindra.model.request.StudentUpdate;
import com.unindra.model.response.StudentResponse;

public interface StudentService {
    
    StudentResponse add(StudentRequest request);

    StudentResponse getByStudentId(String studentId);

    List<StudentResponse> getAll();

    StudentResponse update(String studentId, StudentUpdate request);

    void delete(String studentId);

}