package com.unindra.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentTable {

    private String studentId;

    private String name;

    private String gender;

    private String regencyName;

    private String birthDate;

    private String department;

    private String classroom;

    private String section;

}
