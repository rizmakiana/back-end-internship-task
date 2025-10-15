package com.unindra.service;

import java.util.List;
import java.util.Optional;

import com.unindra.entity.Classroom;
import com.unindra.entity.Department;
import com.unindra.model.request.ClassroomRequest;
import com.unindra.model.response.ClassroomResponse;

public interface ClassroomService {
    
    ClassroomResponse add(ClassroomRequest request);

    List<ClassroomResponse> getAll();

    void delete(String code);

    Optional<Classroom> findByDepartmentAndName(Department department, String classroomGrade);
}
