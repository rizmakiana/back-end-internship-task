package com.unindra.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassroomRequest {
 
    @NotBlank
    private String departmentName;

    @NotBlank
    private String gradeLevel;

}