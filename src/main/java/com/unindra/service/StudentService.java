package com.unindra.service;

import java.util.List;

import com.unindra.model.request.StudentRequest;
import com.unindra.model.request.StudentUpdate;
import com.unindra.model.response.StudentResponse;
import com.unindra.model.response.StudentTable;

public interface StudentService {
    
    StudentResponse add(StudentRequest request);

    StudentResponse getByStudentId(String studentId);

    List<StudentTable> getAll();

    StudentResponse update(String studentId, StudentUpdate request);

    void delete(String studentId);

}