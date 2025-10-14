package com.unindra.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unindra.entity.Classroom;
import com.unindra.model.request.ClassroomRequest;
import com.unindra.model.response.ClassroomResponse;
import com.unindra.repository.ClassroomRepository;
import com.unindra.service.ClassroomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository repository;

    @Override
    public ClassroomResponse add(ClassroomRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public List<ClassroomResponse> getAll() {
        return repository.findAll().stream()
                .map(classroom -> getClassroomResponse(classroom))
                .toList();
    }

    @Override
    public void delete(String code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
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
