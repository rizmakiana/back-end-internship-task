package com.unindra.model.response;

import com.unindra.model.util.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponse {
    
    private String studentId;

    private String name;

    private Gender gender;

    private String regencyId;

    private String birthDate;

    private Integer birthMonth;

    private String birthYear;

    private String districtId;

    private String address;

    private String username;

    private String email;

    private String phoneNumber;

    private String department;

    private String classroom;

    private String section;

}