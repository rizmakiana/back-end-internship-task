package com.unindra.service.impl;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.Classroom;
import com.unindra.entity.Department;
import com.unindra.entity.District;
import com.unindra.entity.Regency;
import com.unindra.entity.Section;
import com.unindra.entity.Student;
import com.unindra.model.request.StudentRequest;
import com.unindra.model.request.StudentUpdate;
import com.unindra.model.response.StudentResponse;
import com.unindra.model.util.Role;
import com.unindra.repository.StudentRepository;
import com.unindra.service.ClassroomService;
import com.unindra.service.DepartmentService;
import com.unindra.service.DistrictService;
import com.unindra.service.RegencyService;
import com.unindra.service.StudentService;
import com.unindra.service.ValidationService;
import com.unindra.util.TimeFormat;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    private final ValidationService validationService;

    private final RegencyService regencyService;

    private final DistrictService districtService;

    private final DepartmentService departmentService;

    private final PasswordEncoder passwordEncoder;

    private final ClassroomService classroomService;

    @Override
    public StudentResponse add(StudentRequest request) {
        validationService.validate(request);

        LocalDate birthDate;
        try {
            birthDate = LocalDate.of(
                    Integer.parseInt(request.getBirthYear()),
                    request.getBirthMonth(),
                    Integer.parseInt(request.getBirthDate()));
        } catch (DateTimeException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid.date");
        }

        Regency regency = regencyService.findById(request.getBirthPlaceRegency())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "regency.notfound"));

        District district = districtService.findById(request.getDistrictAddress())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "district.notfound"));

        if (repository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nama pengguna telah digunakan");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email telah digunakan");
        }

        if (repository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No. telepon telah digunakan");
        }

        Department department = departmentService.findByDepartmentName(request.getDepartmentName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jurusan tidak ditemukan"));

        Classroom classroom = department.getClassrooms().stream()
                .filter(c -> c.getGradeLevel().contains("10"))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tingkat Kelas 10 tidak ditemukan"));

        Section section = classroom.getSections().stream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tidak ada kelas ditemukan"));

        Student student = new Student();

        student.setStudentId(generateNextStudentId());
        student.setName(request.getName());
        student.setGender(request.getGender());
        student.setBirthplace(regency);
        student.setBirthDate(birthDate);
        student.setDistrictAddress(district);
        student.setAddress(request.getAddress());
        student.setUsername(request.getUsername());
        student.setPassword(passwordEncoder.encode(birthDate.format(TimeFormat.formatter)));
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setSection(section);
        student.setRole(Role.STUDENT);

        return getStudentTableData(repository.save(student));
    }

    @Override
    public List<StudentResponse> getAll() {
        return repository.findAll().stream()
                .map(student -> getStudentTableData(student))
                .toList();
    }

    @Override
    public StudentResponse update(String studentId, StudentUpdate request) {
        Student student = repository.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Data siswa tidak ditemukan"));

        validationService.validate(request);

        LocalDate birthDate;
        try {
            birthDate = LocalDate.of(
                    Integer.parseInt(request.getBirthYear()),
                    request.getBirthMonth(),
                    Integer.parseInt(request.getBirthDate()));
        } catch (DateTimeException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid.date");
        }

        Regency regency = regencyService.findById(request.getBirthPlaceRegency())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "regency.notfound"));

        District district = districtService.findById(request.getDistrictAddress())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "district.notfound"));

        if (repository.existsByUsernameAndIdNot(request.getUsername(), student.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nama pengguna telah digunakan");
        }

        if (repository.existsByEmailAndIdNot(request.getEmail(), student.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email telah digunakan");
        }

        if (repository.existsByPhoneNumberAndIdNot(request.getPhoneNumber(), student.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No. telepon telah digunakan");
        }

        Department department = departmentService.findByDepartmentName(request.getDepartmentName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jurusan tidak ditemukan"));

        Classroom classroom = classroomService.findByDepartmentAndName(department, request.getGradeLevel())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tingkat kelas tidak ditemukan"));

        Section section = classroom.getSections().stream()
                .filter(s -> s.getName().equals(String.valueOf(request.getSection())))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Tidak ada kelas ditemukan"));

        student.setStudentId(generateNextStudentId());
        student.setName(request.getName());
        student.setGender(request.getGender());
        student.setBirthplace(regency);
        student.setBirthDate(birthDate);
        student.setDistrictAddress(district);
        student.setAddress(request.getAddress());
        student.setUsername(request.getUsername());
        student.setPassword(passwordEncoder.encode(birthDate.format(TimeFormat.formatter)));
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setSection(section);
        student.setRole(Role.STUDENT);

        return getStudentTableData(repository.save(student));
    }

    @Override
    public void delete(String studentId) {
        Student student = repository.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Siswa masih memiliki tabungan. Harap tarik semua tabungan terlebih dahulu"));

        repository.delete(student);
    }

    public StudentResponse getStudentTableData(Student student) {
        return StudentResponse.builder()
                .studentId(student.getStudentId())
                .name(student.getName())
                .gender(student.getGender())
                .regencyId(student.getBirthplace().getName())
                .birthDate(student.getBirthDate().format(TimeFormat.formatter2))
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
                .birthDate(student.getBirthDate().format(TimeFormat.formatter))
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

    public String generateNextStudentId() {
        String prefix = String.valueOf(Year.now().getValue());

        // Ambil ID terbesar di DB
        Optional<String> lastIdOpt = repository.findMaxStudentId();

        if (lastIdOpt.isEmpty()) {
            return prefix + "0001"; // kalau belum ada data sama sekali
        }

        String lastId = lastIdOpt.get();

        // Pisahkan tahun dari urutan
        String lastYearPart = lastId.substring(0, 4);
        String lastNumberPart = lastId.substring(4);

        int nextNumber;
        if (lastYearPart.equals(prefix)) {
            // masih di tahun yang sama → lanjut increment
            nextNumber = Integer.parseInt(lastNumberPart) + 1;
        } else {
            // tahun baru → mulai dari 1 lagi
            nextNumber = 1;
        }

        return String.format("%s%04d", prefix, nextNumber);
    }

}
