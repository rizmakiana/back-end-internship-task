package com.unindra.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unindra.entity.Section;
import com.unindra.model.request.SectionRequest;
import com.unindra.model.request.SectionUpdateRequest;
import com.unindra.model.response.SectionResponse;
import com.unindra.repository.SectionRepository;
import com.unindra.service.SectionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository repository;

    @Override
    public SectionResponse add(SectionRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public List<SectionResponse> getAll() {
        return repository.findAll().stream()
                .map(sect -> getSectionResponse(sect))
                .toList();
    }

    @Override
    public SectionResponse update(String code, SectionUpdateRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public SectionResponse getSectionResponse(Section section) {
        return SectionResponse.builder()
                .code(section.getCode())
                .departmentName(section.getClassroom().getDepartment().getName())
                .gradeLevel(section.getClassroom().getGradeLevel())
                .name(section.getName())
                .totalStudents(section.getStudents().size())
                .build();
    }

}
