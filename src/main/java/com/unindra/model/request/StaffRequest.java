package com.unindra.model.request;

import com.unindra.model.util.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StaffRequest {

    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

    @NotBlank
    private String birthPlaceRegency;

    @NotBlank
    private String birthDate;

    @NotNull
    private Integer birthMonth;

    @NotBlank
    private String birthYear;

    @NotBlank
    private String districtAddress;

    @NotBlank
    private String address;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @Email
    @NotBlank
    private String email;

    @Size(min = 10, max = 15)
    @Pattern(regexp = "^[0-9+]+$")
    @NotBlank
    private String phoneNumber;
    
}
