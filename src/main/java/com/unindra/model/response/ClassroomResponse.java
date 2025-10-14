package com.unindra.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassroomResponse {
    
    private String code;

    private String departmentName;

    private String gradeLevel;

    private Integer totalSection;

}