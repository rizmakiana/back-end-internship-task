package com.unindra.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.Classroom;
import com.unindra.entity.Department;
import com.unindra.entity.Section;
import com.unindra.model.request.SectionRequest;
import com.unindra.model.request.SectionUpdateRequest;
import com.unindra.model.response.SectionResponse;
import com.unindra.repository.SectionRepository;
import com.unindra.service.ClassroomService;
import com.unindra.service.DepartmentService;
import com.unindra.service.SectionService;
import com.unindra.service.ValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository repository;

    private final ValidationService validationService;

    private final DepartmentService departmentService;

    private final ClassroomService classroomService;

    @Override
    public SectionResponse add(SectionRequest request) {
        validationService.validate(request);

        Department department = departmentService.findByDepartmentName(request.getDepartmentName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Jurusan tidak ditemukan"));

        Classroom classroom = classroomService.findByDepartmentAndName(department, request.getGradeLevel())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Tingkat kelas tidak ditemukan"));

        Character name = getSectionNameList(classroom);
        Section section = new Section();
        section.setClassroom(classroom);
        section.setName(name);
        section.setCode(String.format("%s %s", classroom.getCode(), name));

        return getSectionResponse(repository.save(section));
    }

    @Override
    public List<SectionResponse> getAll() {
        return repository.findAll().stream()
                .map(sect -> getSectionResponse(sect))
                .toList();
    }

    @Override
    public SectionResponse update(String code, SectionUpdateRequest request) {
        validationService.validate(request);

        Section section = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kelas tidak ditemukan"));

        Classroom classroom = section.getClassroom();

        repository.findByClassroomAndCode(classroom, code).ifPresent(existing -> {
            if (!existing.getId().equals(section.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Nama kelas sudah ada");
            }
        });

        String oldCode = section.getCode();
        String prefix = oldCode.substring(0, oldCode.lastIndexOf(" "));
        String name = prefix + " " + request.getName();

        section.setName(request.getName().charAt(0));
        section.setCode(name);

        return getSectionResponse(repository.save(section));
    }

    @Override
    public void delete(String code) {
        Section section = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kelas tidak ditemukan"));

        if (!section.getStudents().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kelas tidak dapat dihapus karena kelas memilki siswa aktif");
        }

        repository.delete(section);
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

    @Transactional(readOnly = true)
    public Character getSectionNameList(Classroom classroom) {
        char last = 'A' - 1; // supaya kalau belum ada section, hasilnya 'A'
        for (Section section : classroom.getSections()) {
            if (section.getName() > last) {
                last = section.getName();
            }
        }
        if (last == 'Z') {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Sudah mencapai maksimum jumlah kelas");
        }

        return (char) (last + 1); // next huruf
    }

}
