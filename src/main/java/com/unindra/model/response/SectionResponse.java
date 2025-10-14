package com.unindra.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SectionResponse {
    
    private String code;

    private String departmentName;

    private String gradeLevel;

    private String name;

    private Integer totalStudents;

}