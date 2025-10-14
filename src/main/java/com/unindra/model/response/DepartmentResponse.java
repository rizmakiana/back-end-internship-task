package com.unindra.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentResponse {

    private String departmentName;

    private String code;

    private Integer totalClassroom;
    
}