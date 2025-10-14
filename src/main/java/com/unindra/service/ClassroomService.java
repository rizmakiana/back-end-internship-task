package com.unindra.service;

import java.util.List;

import com.unindra.model.request.ClassroomRequest;
import com.unindra.model.response.ClassroomResponse;

public interface ClassroomService {
    
    ClassroomResponse add(ClassroomRequest request);

    List<ClassroomResponse> getAll();

    void delete(String code);
}
