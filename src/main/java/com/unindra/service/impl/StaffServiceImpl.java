package com.unindra.service.impl;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unindra.entity.District;
import com.unindra.entity.Regency;
import com.unindra.entity.Staff;
import com.unindra.model.request.StaffRequest;
import com.unindra.model.util.Role;
import com.unindra.repository.StaffRepository;
import com.unindra.service.DistrictService;
import com.unindra.service.RegencyService;
import com.unindra.service.StaffService;
import com.unindra.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService{

    private final StaffRepository repository;

    private final ValidationService validationService;

    private final RegencyService regencyService;

    private final DistrictService districtService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void registration(StaffRequest request) {
        validationService.validate(request);

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password harus sama");
        }

        LocalDate birthDate;
        try {
            birthDate = LocalDate.of(
                    Integer.parseInt(request.getBirthYear()),
                    request.getBirthMonth(),
                    Integer.parseInt(request.getBirthDate()));
        } catch (DateTimeException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tanggal tidak valid");
        }

        Regency regency = regencyService.findById(request.getBirthPlaceRegency())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kota tidak ditemukan"));

        District district = districtService.findById(request.getDistrictAddress())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kecamatan tidak ditemukan"));

        if (repository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nama pengguna telah digunakan");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email telah digunakan");
        }

        if (repository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No. telepon telah digunakan");
        }

        Staff staff = new Staff();

        staff.setName(request.getName());
        staff.setGender(request.getGender());
        staff.setBirthplace(regency);
        staff.setBirthDate(birthDate);
        staff.setDistrictAddress(district);
        staff.setAddress(request.getAddress());
        staff.setUsername(request.getUsername());
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staff.setEmail(request.getEmail());
        staff.setPhoneNumber(request.getPhoneNumber());
        staff.setRole(Role.STAFF);

        repository.save(staff);
    }
}
